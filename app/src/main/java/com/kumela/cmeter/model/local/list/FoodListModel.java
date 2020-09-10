package com.kumela.cmeter.model.local.list;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.api.food.Measure;

import java.util.Arrays;

/**
 * Created by Toko on 28,July,2020
 **/

public class FoodListModel {
    public String foodId;
    public String label;
    public float calories;
    public Measure[] measures;

    public FoodListModel(String foodId, String label, float calories, Measure[] measures) {
        this.foodId = foodId;
        this.label = label;
        this.calories = calories;
        this.measures = measures;
    }

    @NonNull
    @Override
    public String toString() {
        return "AddableFoodListModel{" +
                "food='" + label + '\'' +
                ", calories='" + calories + '\'' +
                ", measures='" + Arrays.toString(measures) + '\'' +
                ", foodId='" + foodId + '\'' +
                '}';
    }
}
