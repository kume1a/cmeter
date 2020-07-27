package com.kumela.cmeter.model.api.nutrition;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.kumela.cmeter.model.api.Photo;

import java.util.List;
import java.util.Locale;

/**
 * Created by Toko on 20,June,2020
 **/

// TODO: 7/21/2020 bug in changing alt measure and result getting 10x more
public class NutritionInfo {
    @SerializedName("food_name")
    public String foodName;

    @SerializedName("serving_qty")
    public float currentServingQuantity;

    @SerializedName("serving_unit")
    public String servingUnit;

    @SerializedName("serving_weight_grams")
    public float servingWeightInGrams;

    @SerializedName("nf_calories")
    public float totalCalories;

    @SerializedName("nf_total_carbohydrate")
    public float totalCarbohydrates;

    @SerializedName("nf_total_fat")
    public float totalFats;

    @SerializedName("nf_protein")
    public float totalProteins;

    @SerializedName("full_nutrients")
    public List<FullNutrient> fullNutrients;

    @SerializedName("alt_measures")
    public List<AltMeasure> altMeasures;

    public Photo photo;

    public NutritionInfo(String foodName,
                          float currentServingQuantity,
                          String servingUnit,
                          float servingWeightInGrams,
                          float totalCalories,
                          float totalCarbohydrates,
                          float totalFats,
                          float totalProteins,
                          List<FullNutrient> fullNutrients
    ) {
        this.foodName = foodName;
        this.currentServingQuantity = currentServingQuantity;
        this.servingUnit = servingUnit;
        this.servingWeightInGrams = servingWeightInGrams;
        this.totalCalories = totalCalories;
        this.totalCarbohydrates = totalCarbohydrates;
        this.totalFats = totalFats;
        this.totalProteins = totalProteins;
        this.fullNutrients = fullNutrients;
    }

    public NutritionInfo getZeroedOutInstance() {
        return new NutritionInfo(
                foodName, currentServingQuantity, servingUnit, servingWeightInGrams,
                0, 0, 0, 0,
                null
        );
    }

    private float servingQuantity;
    public boolean zeroedOut = false;

    public float getServingQuantity() {
        return servingQuantity;
    }

    /**
     * set new alt measure for NutritionInfo class
     *
     * @param altMeasure new AltMeasure
     * @return true if anything changed false otherwise
     */
    public boolean setAltMeasure(AltMeasure altMeasure) {
        zeroedOut = false;
        float valueFraction = altMeasure.servingWeight / this.servingWeightInGrams;

        if (valueFraction == 1.0f) return false;

        if (this.servingQuantity == 0f) this.servingQuantity = this.currentServingQuantity;

        if (this.currentServingQuantity != this.servingQuantity) {
            valueFraction *= this.servingQuantity / this.currentServingQuantity;
        }

        this.servingQuantity = altMeasure.qty;
        this.currentServingQuantity = altMeasure.qty;
        this.servingWeightInGrams = altMeasure.servingWeight;
        this.servingUnit = altMeasure.measure;

        this.totalCalories *= valueFraction;
        this.totalCarbohydrates *= valueFraction;
        this.totalFats *= valueFraction;
        this.totalProteins *= valueFraction;

        for (FullNutrient fullNutrient : this.fullNutrients) {
            fullNutrient.value *= valueFraction;
        }

        roundValues();

        return true;
    }

    public void setCurrentServingQuantity(float currentServingQuantity) {
        if (currentServingQuantity == 0f) {
            zeroedOut = true;
            return;
        } else zeroedOut = false;

        final float valueFraction = currentServingQuantity / this.currentServingQuantity;

        this.currentServingQuantity = currentServingQuantity;

        this.totalCalories *= valueFraction;
        this.totalCarbohydrates *= valueFraction;
        this.totalFats *= valueFraction;
        this.totalProteins *= valueFraction;

        for (FullNutrient fullNutrient : this.fullNutrients) {
            fullNutrient.value *= valueFraction;
        }

        roundValues();
    }

    private void roundValues() {
        this.totalCalories = Float.parseFloat(String.format(Locale.getDefault(), "%.1f", totalCalories));
        this.totalCarbohydrates = Float.parseFloat(String.format(Locale.getDefault(), "%.1f", totalCarbohydrates));
        this.totalFats = Float.parseFloat(String.format(Locale.getDefault(), "%.1f", totalFats));
        this.totalProteins = Float.parseFloat(String.format(Locale.getDefault(), "%.1f", totalProteins));

        for (FullNutrient fullNutrient : this.fullNutrients) {
            fullNutrient.value = Float.parseFloat(String.format(Locale.getDefault(), "%.1f", fullNutrient.value));
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", servingQuantity=" + currentServingQuantity +
                ", servingUnit='" + servingUnit + '\'' +
                ", servingWeightGrams=" + servingWeightInGrams +
                ", calories=" + totalCalories +
                ", totalFat=" + totalFats +
                ", totalCarbohydrates=" + totalCarbohydrates +
                ", protein=" + totalProteins +
                ", fullNutrients=" + fullNutrients +
                ", altMeasures=" + altMeasures +
                ", photo=" + photo +
                '}';
    }
}
