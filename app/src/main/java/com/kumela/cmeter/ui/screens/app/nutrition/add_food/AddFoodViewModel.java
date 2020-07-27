package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.model.firebase.AddedFood;
import com.kumela.cmeter.network.api.nutrition.FetchNutritionInfoUseCase;
import com.kumela.cmeter.network.firebase.FirebaseProductManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Toko on 22,July,2020
 **/

public class AddFoodViewModel extends ViewModel implements FetchNutritionInfoUseCase.Listener {

    private static final String TAG = "AddFoodViewModel";

    private MutableLiveData<List<SearchItem>> mRecentLiveData;
    private MutableLiveData<List<SearchItem>> mFavoritesLiveData;

    public LiveData<List<SearchItem>> getRecentLiveData() {
        return mRecentLiveData;
    }

    public LiveData<List<SearchItem>> getFavoritesLiveData() {
        return mFavoritesLiveData;
    }

    private String mMeal;

    private final String mUserId;
    private final FirebaseFirestore mFirebaseFirestore;

    private final FetchNutritionInfoUseCase mFetchNutritionInfoUseCase;
    private final FirebaseProductManager mFirebaseProductManager;

    public AddFoodViewModel(String userId,
                            FirebaseFirestore firebaseFirestore,
                            FetchNutritionInfoUseCase fetchNutritionInfoUseCase,
                            FirebaseProductManager firebaseProductManager) {
        mUserId = userId;
        mFirebaseFirestore = firebaseFirestore;
        mFetchNutritionInfoUseCase = fetchNutritionInfoUseCase;
        mFirebaseProductManager = firebaseProductManager;

        mRecentLiveData = new MutableLiveData<>();
        mFavoritesLiveData = new MutableLiveData<>();

        mFetchNutritionInfoUseCase.registerListener(this);
    }

    public void fetchRecentlyAddedProducts() {
        mFirebaseFirestore.collection(Constants.COLLECTION_PRODUCTS)
                .whereEqualTo(Constants.UID, mUserId)
                .limit(15)
                .addSnapshotListener((value, error) -> {
                    if (value == null || error != null) {
                        Log.e(TAG, "onCancelled: ", error);
                        mRecentLiveData.setValue(null);
                        return;
                    }

                    Set<SearchItem> recentlyAdded = getSearchItemsFromSnapshot(value);
                    Log.d(TAG, "fetchRecentlyAddedProducts: recently added = " + recentlyAdded);
                    mRecentLiveData.setValue(new ArrayList<>(recentlyAdded));
                });
    }

    public void fetchFavorites() {
        mFirebaseFirestore.collection(Constants.COLLECTION_PRODUCTS)
                .whereEqualTo(Constants.FAVORITE, true)
                .addSnapshotListener((value, error) -> {
                    if (value == null || error != null) {
                        Log.e(TAG, "onCancelled: ", error);
                        mRecentLiveData.setValue(null);
                        return;
                    }

                    Set<SearchItem> favorites = getSearchItemsFromSnapshot(value);
                    mFavoritesLiveData.setValue(new ArrayList<>(favorites));
                });
    }

    @SuppressWarnings("ConstantConditions")
    private Set<SearchItem> getSearchItemsFromSnapshot(QuerySnapshot queryDocumentSnapshots) {
        Set<SearchItem> list = new HashSet<>();
        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
            AddedFood nutritionInfo = snapshot.toObject(AddedFood.class);
            list.add(new SearchItem(
                    nutritionInfo.foodName,
                    nutritionInfo.servingUnit,
                    nutritionInfo.currentServingQuantity,
                    Objects.hash(nutritionInfo.foodName)
            ));
        }
        return list;
    }

    public void writeProduct(String foodName, String meal) {
        mFetchNutritionInfoUseCase.fetchNutritionInfoAndNotify(foodName);
        mMeal = meal;
    }

    @Override
    public void onNutritionInfoFetched(NutritionInfo nutritionInfo) {
        mFirebaseProductManager.writeProductAndNotify(nutritionInfo, mMeal);
    }

    @Override
    public void onNutritionInfoFetchFailed() {
        // TODO: 7/23/2020 Failed is ignored implement notify mechanism in UI
    }
}
