package com.kumela.cmeter.model.api.food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Toko on 27,July,2020
 **/

public class Food {
    public String foodId;
    public String label;
    public Nutrients nutrients;
    public String category;

    @Nullable
    public String brand;
    public String categoryLabel;
    public String image;

    @NonNull
    @Override
    public String toString() {
        return "Parsed{" +
                "foodId='" + foodId + '\'' +
                ", label='" + label + '\'' +
                ", nutrients=" + nutrients +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", categoryLabel='" + categoryLabel + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
