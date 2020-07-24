package com.kumela.cmeter.network.firebase;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.firebase.AddedFood;

import javax.inject.Inject;

/**
 * Created by Toko on 13,July,2020
 **/

public class FirebaseProductManager extends BaseObservable<FirebaseProductManager.Listener> {

    private static final String TAG = "FirebaseProductManager";

    public interface Listener {
        void onDatabaseWriteCompleted();
    }

    private FirebaseFirestore mFirestore;
    private String mUserId;

    @Inject
    public FirebaseProductManager(FirebaseFirestore firebaseFirestore, String uid) {
        this.mFirestore = firebaseFirestore;
        this.mUserId = uid;
    }

    public void writeProductAndNotify(NutritionInfo nutritionInfo, String mealType) {
        AddedFood addedFood = new AddedFood(
                mUserId,
                mealType,
                nutritionInfo.foodName,
                false,
                nutritionInfo.currentServingQuantity,
                nutritionInfo.servingUnit,
                nutritionInfo.servingWeightInGrams,
                nutritionInfo.totalCalories,
                nutritionInfo.totalCarbohydrates,
                nutritionInfo.totalFats,
                nutritionInfo.totalProteins,
                nutritionInfo.fullNutrients,
                nutritionInfo.altMeasures,
                nutritionInfo.photo.highRes,
                nutritionInfo.getServingQuantity(),
                nutritionInfo.zeroedOut
        );

        Log.d(TAG, "writeProductAndNotify: called, addedFood = " + addedFood);
        mFirestore.collection(Constants.COLLECTION_PRODUCTS)
                .add(addedFood)
                .addOnSuccessListener(aVoid -> {
                    for (Listener listener : getListeners()) listener.onDatabaseWriteCompleted();
                }).
                addOnFailureListener(e -> Log.e(getClass().getSimpleName(), "writeFoodAndNotify: ", e));

    }
}
