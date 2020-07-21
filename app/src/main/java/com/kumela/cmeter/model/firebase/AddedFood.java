package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.api.nutrition.AltMeasure;
import com.kumela.cmeter.model.api.nutrition.FullNutrient;
import com.kumela.cmeter.model.local.BaseNutrition;

import java.util.List;

/**
 * Created by Toko on 14,July,2020
 **/

public class AddedFood {
    public String uid_date;
    public String name;
    public String date;
    public String mealType;

    public BaseNutrition baseNutrition;
    public Float quantity;
    public String servingUnit;
    public Float servingWeightInGrams;

    public List<FullNutrient> fullNutrients;
    public List<AltMeasure> altMeasures;
    public String imageUrl;

    @SuppressWarnings("unused")
    public AddedFood() {
    }

    public AddedFood(String uid,
                     String mealType,
                     String name,
                     String date,
                     BaseNutrition baseNutrition,
                     Float quantity,
                     String servingUnit,
                     Float servingWeightInGrams,
                     List<FullNutrient> fullNutrients,
                     List<AltMeasure> altMeasures,
                     String imageUrl) {
        this.uid_date = uid + date;
        this.mealType = mealType;
        this.name = name;
        this.date = date;
        this.baseNutrition = baseNutrition;
        this.quantity = quantity;
        this.servingUnit = servingUnit;
        this.servingWeightInGrams = servingWeightInGrams;
        this.fullNutrients = fullNutrients;
        this.altMeasures = altMeasures;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Product{" + "\n" +
                "uid_date='" + uid_date + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", timestamp='" + date + '\'' + "\n" +
                ", baseNutrition=" + baseNutrition + "\n" +
                '}';
    }
}
