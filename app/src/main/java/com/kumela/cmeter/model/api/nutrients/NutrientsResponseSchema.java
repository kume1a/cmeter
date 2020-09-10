package com.kumela.cmeter.model.api.nutrients;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Toko on 27,July,2020
 **/

public class NutrientsResponseSchema {
    public String uri;
    public float calories;
    public float totalWeight;
    public List<String> dietLabels;
    public List<String> healthLabels;
    public List<String> cautions;

    public TotalNutrients totalNutrients;
    public List<Ingredients> ingredients;

    @Override
    public String toString() {
        return "NutrientsResponseSchema{" +
                "uri='" + uri + '\'' +
                ", calories=" + calories +
                ", totalWeight=" + totalWeight +
                ", dietLabels=" + dietLabels +
                ", healthLabels=" + healthLabels +
                ", cautions=" + cautions +
                ", totalNutrients=" + totalNutrients +
                ", ingredients=" + ingredients +
                '}';
    }
}
