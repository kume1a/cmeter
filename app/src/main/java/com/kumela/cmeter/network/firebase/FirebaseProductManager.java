package com.kumela.cmeter.network.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.common.Utils;
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

    private DatabaseReference mDatabase;
    private String mUserId;

    @Inject
    public FirebaseProductManager(FirebaseDatabase firebaseDatabase, String uid) {
        this.mDatabase = firebaseDatabase.getReference();
        this.mUserId = uid;
    }

    public void writeProductAndNotify(NutritionInfo nutritionInfo, String mealType) {
        AddedFood addedFood = new AddedFood(
                mUserId + Utils.getDate(),
                mealType,
                nutritionInfo.foodName,
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
        mDatabase.child(Constants.CHILD_PRODUCTS)
                .push()
                .setValue(addedFood)
                .addOnSuccessListener(aVoid -> {
                    for (Listener listener : getListeners()) listener.onDatabaseWriteCompleted();
                }).
                addOnFailureListener(e -> Log.e(getClass().getSimpleName(), "writeFoodAndNotify: ", e));

    }
}
