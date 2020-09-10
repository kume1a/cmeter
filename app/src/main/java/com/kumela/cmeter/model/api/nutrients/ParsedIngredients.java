package com.kumela.cmeter.model.api.nutrients;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 28,July,2020
 **/

public class ParsedIngredients {
    public float quantity;
    public String food;
    public String foodId;
    public float weight;
    public float retainedWeight;
    @SerializedName("measureURI")
    public String measureUri;
    public String status;

    @NonNull
    @Override
    public String toString() {
        return "ParsedIngredients{" +
                "quantity=" + quantity +
                ", food='" + food + '\'' +
                ", foodId='" + foodId + '\'' +
                ", weight=" + weight +
                ", retainedWeight=" + retainedWeight +
                ", measureUri='" + measureUri + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
