package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.network.api.nutrition.FetchNutritionInfoUseCase;
import com.kumela.cmeter.network.api.search.FetchSearchResultsUseCase;
import com.kumela.cmeter.network.firebase.FirebaseProductManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Toko on 01,July,2020
 **/

public class SearchViewModel extends ViewModel
        implements FetchSearchResultsUseCase.Listener, FetchNutritionInfoUseCase.Listener {

    private static final String TAG = "SearchViewModel";

    interface Listener {
        void onSearchItemsFetched(Set<SearchItem> searchItems);

        void onSearchItemsFetchFailed();
    }

    private Set<Listener> mListeners = new HashSet<>(1);
    private Set<SearchItem> mSearchItems = new HashSet<>();

    private String mQuery = "";

    private final FetchSearchResultsUseCase mFetchSearchResultsUseCase;
    private final FetchNutritionInfoUseCase mFetchNutritionInfoUseCase;
    private final FirebaseProductManager mFirebaseProductManager;

    private String mMealType;

    public SearchViewModel(FetchSearchResultsUseCase fetchSearchResultsUseCase,
                           FetchNutritionInfoUseCase fetchNutritionInfoUseCase,
                           FirebaseProductManager firebaseProductManager) {
        this.mFetchSearchResultsUseCase = fetchSearchResultsUseCase;
        this.mFetchNutritionInfoUseCase = fetchNutritionInfoUseCase;

        this.mFirebaseProductManager = firebaseProductManager;

        mFetchSearchResultsUseCase.registerListener(this);
        mFetchNutritionInfoUseCase.registerListener(this);
    }

    void fetchSearchResultsAndNotify(@NonNull String query) {
        Log.d(TAG, "fetchSearchResultsAndNotify: called, query = " + query + ", mQuery = " + mQuery);
        if (!query.equals(mQuery)) {
            Log.d(TAG, "fetchSearchResultsAndNotify: fetching from use case");
            mFetchSearchResultsUseCase.fetchSearchItemsAndNotify(query);
        } else {
            Log.d(TAG, "fetchSearchResultsAndNotify: getting list from view model");
            onSearchItemsFetched(mSearchItems);
        }
        mQuery = query;
    }

    void writeProduct(String foodName, String mealType) {
        mFetchNutritionInfoUseCase.fetchNutritionInfoAndNotify(foodName);
        mMealType = mealType;
    }

    String getQuery() {
        return mQuery;
    }

    public Set<SearchItem> getSearchItems() {
        return mSearchItems;
    }

    @Override
    public void onSearchItemsFetched(Set<SearchItem> searchItems) {
        mSearchItems = searchItems;
        for (Listener listener : mListeners) {
            listener.onSearchItemsFetched(searchItems);
        }
    }

    @Override
    public void onSearchItemsFetchFailed() {
        for (Listener listener : mListeners) {
            listener.onSearchItemsFetchFailed();
        }
    }

    @Override
    public void onNutritionInfoFetched(NutritionInfo nutritionInfo) {
        mFirebaseProductManager.writeProductAndNotify(nutritionInfo, mMealType);
    }

    @Override
    public void onNutritionInfoFetchFailed() {
        // TODO: 7/14/2020 Failed is ignored implement notify mechanism in UI
    }

    void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mFetchSearchResultsUseCase.unregisterListener(this);
        mFetchNutritionInfoUseCase.unregisterListener(this);
    }
}
