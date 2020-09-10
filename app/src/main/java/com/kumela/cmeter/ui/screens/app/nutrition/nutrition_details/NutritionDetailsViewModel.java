package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.firebase.FirebaseFoodNutrients;
import com.kumela.cmeter.model.firebase.FirebaseProduct;
import com.kumela.cmeter.model.local.FoodNutrients;
import com.kumela.cmeter.network.api.nutrients.FetchNutrientsUseCase;
import com.kumela.cmeter.network.firebase.FirebaseProductManager;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewModel;

import java.util.List;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsViewModel extends ObservableViewModel<NutritionDetailsViewModel.Listener>
        implements FirebaseProductManager.Listener, FetchNutrientsUseCase.Listener {

    private static final String TAG = "NutritionDetailsViewMod";

    public interface Listener {
        void onProvideFoodNutrients(FoodNutrients foodNutrients);

        void onProvideFoodNutrientsFailed();

        void onAddFoodSucceeded();

        void onAddFoodFailed();
    }

    // cached food nutrients model
    private FoodNutrients mFoodNutrients;
    private String cachedMeasureUri;

    private final FetchNutrientsUseCase mFetchNutrientsUseCase;
    private final FirebaseProductManager mFirebaseProductManager;
    private final FirebaseFirestore mFirestore;

    public NutritionDetailsViewModel(FetchNutrientsUseCase fetchNutrientsUseCase,
                                     FirebaseProductManager firebaseProductManager,
                                     FirebaseFirestore firebaseFirestore) {
        this.mFetchNutrientsUseCase = fetchNutrientsUseCase;
        this.mFirebaseProductManager = firebaseProductManager;
        this.mFirestore = firebaseFirestore;

        mFetchNutrientsUseCase.registerListener(this);
        mFirebaseProductManager.registerListener(this);
    }

    private void notifySuccess(FoodNutrients foodNutrients) {
        for (Listener listener : getListeners()) {
            listener.onProvideFoodNutrients(foodNutrients);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onProvideFoodNutrientsFailed();
        }
    }

    void fetchNutritionInfoAndNotify(String foodId, float quantity, String measureUri) {
        if (!measureUri.equals(cachedMeasureUri)) {
            fetchNutrientsAndNotify(foodId, quantity, measureUri);
        } else {
            if (mFoodNutrients != null) {
                notifySuccess(mFoodNutrients);
            } else fetchNutrientsAndNotify(foodId, quantity, measureUri);
        }

        this.cachedMeasureUri = measureUri;
    }

    @SuppressWarnings("SameParameterValue")
    void writeProduct(List<Measure> measures, String meal, boolean favorite) {
        FoodNutrients foodNutrients = mFoodNutrients;

        Log.d(TAG, "writeProduct: called, foodNutrients = " + foodNutrients);
        if (foodNutrients != null) {
            mFirebaseProductManager.writeProductAndNotify(
                    foodNutrients.foodId, foodNutrients.quantity,
                    measures, foodNutrients.measureUri,
                    meal, favorite
            );
        }
    }

    private void fetchNutrientsAndNotify(String foodId, float quantity, String measureUri) {
        mFirestore.collection(Constants.COLLECTION_PRODUCTS)
                .whereEqualTo(FirebaseFoodNutrients.FOOD_ID, foodId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                        getFoodNutrientsFromSnapshotAndNotify(snapshot, foodId, quantity, measureUri);
                    } else {
                        mFetchNutrientsUseCase.fetchNutrientsAndNotify(foodId, quantity, measureUri);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "fetchNutrientsAndNotify: ", e);
                    mFetchNutrientsUseCase.fetchNutrientsAndNotify(foodId, quantity, measureUri);
                });
    }

    private void getFoodNutrientsFromSnapshotAndNotify(DocumentSnapshot snapshot,
                                                       String foodId,
                                                       float quantity,
                                                       String measureUri) {
        snapshot.getReference()
                .collection(Constants.COLLECTION_PRODUCT_MEASURES)
                .whereEqualTo(FirebaseProduct.MEASURE_URI, measureUri)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    @SuppressWarnings("ConstantConditions")
                    FoodNutrients foodNutrients = queryDocumentSnapshots
                            .getDocuments()
                            .get(0)
                            .toObject(FirebaseProduct.class)
                            .foodNutrients;

                    Log.d(TAG, "getFoodNutrientsFromSnapshotAndNotify: notifying from firebase info");
                    mFoodNutrients = foodNutrients;
                    notifySuccess(mFoodNutrients);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "getFoodNutrientsFromSnapshotAndNotify: ", e);
                    mFetchNutrientsUseCase.fetchNutrientsAndNotify(foodId, quantity, measureUri);
                });
    }

    @Override
    public void onNutritionFetched(FoodNutrients foodNutrients) {
        mFoodNutrients = foodNutrients;
        notifySuccess(mFoodNutrients);
    }

    @Override
    public void onNutritionFetchFailed() {
        notifyFailure();
    }

    @Override
    public void onAddFoodSucceeded() {
        for (Listener listener : getListeners()) {
            listener.onAddFoodSucceeded();
        }
    }

    @Override
    public void onAddFoodFailed() {
        for (Listener listener : getListeners()) {
            listener.onAddFoodFailed();
        }
    }

    public void onServingQuantityChanged(float servingQuantity) {
        mFoodNutrients.setServingQuantity(servingQuantity);
        notifySuccess(mFoodNutrients);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mFetchNutrientsUseCase.unregisterListener(this);
        mFirebaseProductManager.unregisterListener(this);
    }
}
