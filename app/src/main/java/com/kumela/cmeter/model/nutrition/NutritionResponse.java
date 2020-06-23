package com.kumela.cmeter.model.nutrition;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Toko on 20,June,2020
 **/

public class NutritionResponse {
    public List<Foods> foods;
    public Errors errors;

    @NonNull
    @Override
    public String toString() {
        return "NutritionResponse{" +
                "foods=" + foods +
                ", errors=" + errors +
                '}';
    }
}
