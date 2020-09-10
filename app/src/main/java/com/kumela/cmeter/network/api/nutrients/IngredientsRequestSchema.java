package com.kumela.cmeter.network.api.nutrients;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 29,July,2020
 **/

public class IngredientsRequestSchema {
    public float quantity;
    public String foodId;

    @SerializedName("measureURI")
    public String measureUri;

    public IngredientsRequestSchema(float quantity,
                                    String measureUri,
                                    String foodId) {
        this.quantity = quantity;
        this.measureUri = measureUri;
        this.foodId = foodId;
    }
}
