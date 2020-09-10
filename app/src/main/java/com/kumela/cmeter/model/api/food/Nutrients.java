package com.kumela.cmeter.model.api.food;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 27,July,2020
 **/

public class Nutrients {
    @SerializedName("ENERC_KCAL")
    public float calories;

    @SerializedName("CHOCDF")
    public float carbohydrates;

    @SerializedName("FAT")
    public float fats;

    @SerializedName("PROCNT")
    public float proteins;

    @SerializedName("FIBTG")
    public float fiber;

    @SuppressWarnings("unused")
    public Nutrients() {}

    public Nutrients(float calories, float carbohydrates, float fats, float proteins, float fiber) {
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;
        this.fiber = fiber;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nutrients{" +
                "calories=" + calories +
                ", carbohydrates=" + carbohydrates +
                ", fats=" + fats +
                ", proteins=" + proteins +
                ", fiber=" + fiber +
                '}';
    }
}
