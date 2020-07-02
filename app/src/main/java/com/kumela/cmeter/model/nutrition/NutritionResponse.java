package com.kumela.cmeter.model.nutrition;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Toko on 20,June,2020
 **/

public class NutritionResponse {
    public List<Food> foods;
    public List<Error> errors;

    @NonNull
    @Override
    public String toString() {
        return "NutritionResponse{" +
                "food=" + foods +
                ", error=" + errors +
                '}';
    }
}
