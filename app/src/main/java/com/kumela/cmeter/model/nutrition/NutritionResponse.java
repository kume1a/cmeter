package com.kumela.cmeter.model.nutrition;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Toko on 20,June,2020
 **/

public class NutritionResponse {
    @SerializedName("foods")
    public List<NutritionInfo> mNutritionInfos;
    public List<Error> errors;

    @NonNull
    @Override
    public String toString() {
        return "NutritionResponse{" +
                "food=" + mNutritionInfos +
                ", error=" + errors +
                '}';
    }
}
