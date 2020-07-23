package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.api.nutrition.AltMeasure;
import com.kumela.cmeter.model.api.nutrition.FullNutrient;

import java.util.List;

/**
 * Created by Toko on 14,July,2020
 **/

public class AddedFood {
    public String mealType;
    public String foodName;

    public String uid;
    public String uidDate;
    public String uidDateMeal;
    public String uidFavorite;

    public float currentServingQuantity;
    public String servingUnit;
    public float servingWeightInGrams;

    public float totalCalories;
    public float totalCarbohydrates;
    public float totalFats;
    public float totalProteins;

    public List<FullNutrient> fullNutrients;
    public List<AltMeasure> altMeasures;
    public String imageUrl;

    public float servingQuantity;
    public boolean zeroedOut = false;

    @SuppressWarnings("unused")
    public AddedFood() {
    }

    public AddedFood(String uid,
                     String mealType,
                     String foodName,
                     boolean favorite,
                     float currentServingQuantity,
                     String servingUnit,
                     float servingWeightInGrams,
                     float totalCalories,
                     float totalCarbohydrates,
                     float totalFats,
                     float totalProteins,
                     List<FullNutrient> fullNutrients,
                     List<AltMeasure> altMeasures,
                     String imageUrl,
                     float servingQuantity,
                     boolean zeroedOut
    ) {
        this.uid = uid;
        this.uidDate = uid + Utils.getDate();
        this.uidDateMeal = uidDate + mealType;
        this.mealType = mealType;
        this.uidFavorite = uid + favorite;
        this.foodName = foodName;
        this.currentServingQuantity = currentServingQuantity;
        this.servingUnit = servingUnit;
        this.servingWeightInGrams = servingWeightInGrams;
        this.totalCalories = totalCalories;
        this.totalCarbohydrates = totalCarbohydrates;
        this.totalFats = totalFats;
        this.totalProteins = totalProteins;
        this.fullNutrients = fullNutrients;
        this.altMeasures = altMeasures;
        this.imageUrl = imageUrl;
        this.servingQuantity = servingQuantity;
        this.zeroedOut = zeroedOut;
    }

    @NonNull
    @Override
    public String toString() {
        return "AddedFood{" + "\n" +
                "mealType='" + mealType + '\'' + "\n" +
                "foodName='" + foodName + '\'' + "\n" +
                "currentServingQuantity=" + currentServingQuantity + "\n" +
                "servingUnit='" + servingUnit + '\'' + "\n" +
                "servingWeightInGrams=" + servingWeightInGrams + "\n" +
                "totalCalories=" + totalCalories + "\n" +
                "totalCarbohydrates=" + totalCarbohydrates + "\n" +
                "totalFats=" + totalFats + "\n" +
                "totalProteins=" + totalProteins + "\n" +
                "fullNutrients=" + fullNutrients + "\n" +
                "altMeasures=" + altMeasures + "\n" +
                "imageUrl='" + imageUrl + '\'' + "\n" +
                "servingQuantity=" + servingQuantity + "\n" +
                "zeroedOut=" + zeroedOut + "\n" +
                '}';
    }
}
