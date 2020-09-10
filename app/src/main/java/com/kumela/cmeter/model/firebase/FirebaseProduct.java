package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.local.FoodNutrients;

/**
 * Created by Toko on 05,August,2020
 **/

public class FirebaseProduct {
    public static final String MEASURE_URI = "measureUri";
    public static final String MEASURE_LABEL = "measureLabel";

    public FoodNutrients foodNutrients;
    public String measureUri;
    public String measureLabel;

    @SuppressWarnings("unused")
    public FirebaseProduct() {
    }

    public FirebaseProduct(Measure measure, FoodNutrients foodNutrients) {
        this.measureUri = measure.uri;
        this.measureLabel = measure.label;
        this.foodNutrients = foodNutrients;
    }

    @NonNull
    @Override
    public String toString() {
        return "FirebaseProduct{" +
                "foodNutrients=" + foodNutrients +
                ", measureUri='" + measureUri + '\'' +
                ", measureLabel='" + measureLabel + '\'' +
                '}';
    }
}
