package com.kumela.cmeter.network.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.firebase.AddedFood;
import com.kumela.cmeter.model.local.BaseNutrition;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_PATTERN, Locale.getDefault());
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        BaseNutrition baseNutrition = new BaseNutrition(
                nutritionInfo.totalCalories,
                nutritionInfo.totalCarbohydrates,
                nutritionInfo.totalFats,
                nutritionInfo.totalProteins
        );

        AddedFood addedFood = new AddedFood(
                mUserId,
                mealType,
                nutritionInfo.foodName,
                date,
                baseNutrition,
                nutritionInfo.currentServingQuantity,
                nutritionInfo.servingUnit,
                nutritionInfo.servingWeightInGrams,
                nutritionInfo.fullNutrients,
                nutritionInfo.altMeasures,
                nutritionInfo.photo.highRes
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
