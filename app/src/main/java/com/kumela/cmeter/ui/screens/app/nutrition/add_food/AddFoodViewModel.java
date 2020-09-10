package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.firebase.FirebaseProductHistory;
import com.kumela.cmeter.model.firebase.FirebaseProductHistoryItem;
import com.kumela.cmeter.model.firebase.FirebaseSearchHistory;
import com.kumela.cmeter.model.local.list.ProductHistoryListModel;
import com.kumela.cmeter.model.local.list.SearchSuggestionItem;
import com.kumela.cmeter.network.api.suggestions.FetchSearchSuggestionsUseCase;
import com.kumela.cmeter.network.firebase.FirebaseProductManager;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Toko on 22,July,2020
 **/

public class AddFoodViewModel extends ObservableViewModel<AddFoodViewModel.Listener>
        implements FirebaseProductManager.Listener, FetchSearchSuggestionsUseCase.Listener {

    public interface Listener {
        void onProvideProductHistory(List<ProductHistoryListModel> favorites);

        void onProvideProductHistoryFailed();
    }

    private static final String TAG = "AddFoodViewModel";

    // TODO: 7/28/2020 change list and recyclerView
    private List<ProductHistoryListModel> mProductHistory;

    private MutableLiveData<List<SearchSuggestionItem>> mSuggestionsLiveData;

    public LiveData<List<SearchSuggestionItem>> getSuggestionsLiveData() {
        return mSuggestionsLiveData;
    }

    private final String mUserId;
    private final FirebaseFirestore mFirestore;
    private final FirebaseProductManager mFirebaseProductManager;
    private final FetchSearchSuggestionsUseCase mFetchSearchSuggestionsUseCase;

    public AddFoodViewModel(String userId,
                            FirebaseFirestore firestore,
                            FirebaseProductManager firebaseProductManager,
                            FetchSearchSuggestionsUseCase fetchSearchSuggestionsUseCase) {
        mUserId = userId;
        mFirestore = firestore;
        mFirebaseProductManager = firebaseProductManager;
        mFetchSearchSuggestionsUseCase = fetchSearchSuggestionsUseCase;

        mSuggestionsLiveData = new MutableLiveData<>();

        mFetchSearchSuggestionsUseCase.registerListener(this);
    }

    private void provideProductHistory(List<ProductHistoryListModel> productHistory) {
        for (Listener listener : getListeners()) {
            listener.onProvideProductHistory(productHistory);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onProvideProductHistoryFailed();
        }
    }

    public void fetchProductHistoryAndNotify() {
        if (mProductHistory != null) {
            provideProductHistory(mProductHistory);
        } else fetchProductHistory();
    }

    private void fetchProductHistory() {
        DocumentReference docRef = mFirestore
                .collection(Constants.COLLECTION_PRODUCT_HISTORY)
                .document(mUserId);

        ListenerRegistration listenerRegistration = docRef
                .addSnapshotListener((value, error) -> {
                    if (value == null || error != null) {
                        Log.e(TAG, "onCancelled: ", error);
                        notifyFailure();
                        return;
                    }

                    if (!value.exists()) {
                        mProductHistory = new ArrayList<>();
                        notifyFailure();
                    }

                    FirebaseProductHistory firebaseProductHistory = value.toObject(FirebaseProductHistory.class);
                    if (firebaseProductHistory != null) {
                        List<FirebaseProductHistoryItem> historyItems = firebaseProductHistory.productHistory;

                        Log.d(TAG, "fetchProductHistory: historyItems = " + historyItems);

                        // get duplicates from list and delete if there was any
                        List<FirebaseProductHistoryItem> duplicates = Utils.getDuplicates(historyItems);
                        if (!duplicates.isEmpty()) {
                            Log.d(TAG, "fetchProductHistory: duplicates = " + duplicates);
                            for (FirebaseProductHistoryItem duplicate : duplicates) {
                                docRef.update(FirebaseProductHistory.PRODUCT_HISTORY, FieldValue.arrayRemove(duplicate));
                            }
                        }

                        mProductHistory = new ArrayList<>();
                        for (FirebaseProductHistoryItem historyItem : historyItems) {
                            mProductHistory.add(new ProductHistoryListModel(historyItem));
                        }
                        provideProductHistory(mProductHistory);
                    } else notifyFailure();
                });
        registerSnapshotListener(listenerRegistration);
    }

    /**
     * write and store product in database
     *
     * @param foodId     long string of food's ID
     * @param quantity   quantity of measure
     * @param measureUri uri of measure from api
     * @param meal       meal must be one of BREAKFAST, DINNER, SUPPER, SNACKS
     * @param favorite   boolean indicating if user marked product as favorite
     */
    public void writeProduct(String foodId,
                             float quantity,
                             List<Measure> measures,
                             String measureUri,
                             String meal,
                             boolean favorite) {
        mFirebaseProductManager.writeProductAndNotify(
                foodId,
                quantity,
                measures,
                measureUri,
                meal,
                favorite
        );
    }

    @Override
    public void onAddFoodSucceeded() {
        // TODO: 7/28/2020
    }

    @Override
    public void onAddFoodFailed() {
        // TODO: 7/28/2020
    }

    public void fetchSearchSuggestions(String query, int limit) {
        mFetchSearchSuggestionsUseCase.fetchSearchSuggestionsAndNotify(query, limit);
    }

    @Override
    public void onSearchSuggestionsFetched(String[] suggestions) {
        List<SearchSuggestionItem> suggestionList = new ArrayList<>();
        for (String suggestion : suggestions) {
            suggestionList.add(new SearchSuggestionItem(suggestion, SearchSuggestionItem.INDICATOR_SEARCH));
        }
        mSuggestionsLiveData.setValue(suggestionList);
    }

    @Override
    public void onSearchSuggestionsFetchFailed() {
        mSuggestionsLiveData.setValue(null);
    }

    /**
     * append string in list of previously searched queries (history) and store in database
     *
     * @param query submitted search
     */
    public void writeSearchSuggestion(String query) {
        mFirestore.collection(Constants.COLLECTION_SEARCH_HISTORY)
                .document(mUserId)
                .update(FirebaseSearchHistory.HISTORY, FieldValue.arrayUnion(query));
    }

    // TODO: 8/6/2020 remove listener and refactor to general listener
    public interface SearchHistoryProviderListener {
        void onProvideSearchSuggestions(List<SearchSuggestionItem> searchHistory);
    }

    private List<SearchSuggestionItem> mCachedSearchHistory;

    public void getSearchHistory(SearchHistoryProviderListener listener) {
        if (mCachedSearchHistory == null) {
            DocumentReference searchHistoryRef = mFirestore.collection(Constants.COLLECTION_SEARCH_HISTORY)
                    .document(mUserId);

            searchHistoryRef.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        List<SearchSuggestionItem> history = getSearchSuggestionsFromDocument(documentSnapshot);
                        listener.onProvideSearchSuggestions(history);
                    });

            // add realtime listener and cache list in local variable
            searchHistoryRef.addSnapshotListener((value, error) -> {
                Log.d(TAG, "getSearchHistory: called");
                if (value == null || error != null) {
                    Log.e(TAG, "getSearchHistory: ", error);
                } else mCachedSearchHistory = getSearchSuggestionsFromDocument(value);
            });
        } else {
            listener.onProvideSearchSuggestions(mCachedSearchHistory);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private List<SearchSuggestionItem> getSearchSuggestionsFromDocument(DocumentSnapshot documentSnapshot) {
        List<SearchSuggestionItem> searchHistory = new ArrayList<>();

        if (documentSnapshot.exists()) {
            FirebaseSearchHistory history = documentSnapshot.toObject(FirebaseSearchHistory.class);
            for (String item : history.history) {
                searchHistory.add(
                        new SearchSuggestionItem(item, SearchSuggestionItem.INDICATOR_RECENT)
                );
            }
        }
        Collections.reverse(searchHistory);
        return searchHistory;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mFetchSearchSuggestionsUseCase.unregisterListener(this);
    }
}
