package com.kumela.cmeter.network.api.nutrients;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Toko on 27,July,2020
 **/

public class NutrientsRequestSchema {
    public List<IngredientsRequestSchema> ingredients;

    public NutrientsRequestSchema(List<IngredientsRequestSchema> ingredients) {
        this.ingredients = ingredients;
    }
}
