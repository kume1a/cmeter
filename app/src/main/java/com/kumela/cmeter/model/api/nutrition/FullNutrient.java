package com.kumela.cmeter.model.api.nutrition;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 20,June,2020
 **/

public class FullNutrient {
    @SerializedName("attr_id")
    public int id;

    @SerializedName("value")
    public float value;

    @SuppressWarnings("unused")
    public FullNutrient() {
    }

    public FullNutrient(int id, float value) {
        this.id = id;
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return "FullNutrient{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
