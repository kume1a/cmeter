package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.local.FoodNutrients;

import java.util.Map;

/**
 * Created by Toko on 14,July,2020
 **/

public class FirebaseFoodNutrients {
    public static final String FOOD_ID = "foodId";

    public String foodId;

    @SuppressWarnings("unused")
    public FirebaseFoodNutrients() {
    }

    public FirebaseFoodNutrients(String foodId) {
        this.foodId = foodId;
    }

    @NonNull
    @Override
    public String toString() {
        return "FirebaseFoodNutrients{" + "\n" +
                ", foodId=" + foodId + "\n" +
                '}';
    }
}
