package com.kumela.cmeter.model.api.nutrients;

import androidx.annotation.NonNull;

/**
 * Created by Toko on 27,July,2020
 **/

public class TotalNutrient {
    public String label;
    public float quantity;
    public String unit;

    @SuppressWarnings("unused")
    public TotalNutrient() {}

    public TotalNutrient(String label, float quantity, String unit) {
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }

    @NonNull
    @Override
    public String toString() {
        return "TotalNutrient{" +
                "label='" + label + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
