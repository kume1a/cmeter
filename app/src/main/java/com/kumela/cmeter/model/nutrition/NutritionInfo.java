package com.kumela.cmeter.model.nutrition;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.kumela.cmeter.model.common.Photo;

import java.util.List;

/**
 * Created by Toko on 20,June,2020
 **/

public class NutritionInfo {
    @SerializedName("food_name")
    public String foodName;

    @SerializedName("brand_name")
    public String brandName;

    @SerializedName("serving_qty")
    public float servingQuantity;

    @SerializedName("serving_unit")
    public String servingUnit;

    @SerializedName("serving_weight_grams")
    public float servingWeightGrams;

    @SerializedName("nf_calories")
    public float totalCalories;

    @SerializedName("nf_total_fat")
    public float totalFat;

    @SerializedName("nf_saturated_fat")
    public float saturatedFat;

    @SerializedName("nf_cholesterol")
    public float cholesterol;

    @SerializedName("nf_sodium")
    public float sodium;

    @SerializedName("nf_total_carbohydrate")
    public float totalCarbohydrates;

    @SerializedName("nf_dietary_fiber")
    public float dietaryFiber;

    @SerializedName("nf_sugars")
    public float sugars;

    @SerializedName("nf_protein")
    public float totalProtein;

    @SerializedName("nf_potassium")
    public float potassium;

    @SerializedName("nf_p")
    public float p;

    @SerializedName("full_nutrients")
    public List<FullNutrient> fullNutrients;

    /*@SerializedName("nix_brand_name")
    public String nixBrandName;

    @SerializedName("nix_brand_id")
    public String nixBrandId;

    @SerializedName("nix_item_id")
    public String nixItemId;

    public String upc;*/

    @SerializedName("consumed_at")
    public String timestamp;

    /*public MetaData metaData;

    public int source;

    @SerializedName("ndb_no")
    public int ndbNo;

    public Tags tags;*/

    @SerializedName("alt_measures")
    public List<AltMeasure> altMeasures;

    /*public double lat;
    public double lng;

    @SerializedName("meal_type")
    public int mealType;*/

    public Photo photo;

    /*@SerializedName("sub_recipe")
    public String subRecipe;*/

    @NonNull
    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", servingQuantity=" + servingQuantity +
                ", servingUnit='" + servingUnit + '\'' +
                ", servingWeightGrams=" + servingWeightGrams +
                ", calories=" + totalCalories +
                ", totalFat=" + totalFat +
                ", saturatedFat=" + saturatedFat +
                ", cholesterol=" + cholesterol +
                ", sodium=" + sodium +
                ", totalCarbohydrates=" + totalCarbohydrates +
                ", dietaryFiber=" + dietaryFiber +
                ", sugars=" + sugars +
                ", protein=" + totalProtein +
                ", potassium=" + potassium +
                ", p=" + p +
                ", fullNutrients=" + fullNutrients +
                ", timestamp='" + timestamp + '\'' +
                ", altMeasures=" + altMeasures +
                ", photo=" + photo +
                '}';
    }
}
