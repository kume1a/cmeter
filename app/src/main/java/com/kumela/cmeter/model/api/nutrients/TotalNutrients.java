package com.kumela.cmeter.model.api.nutrients;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 27,July,2020
 **/

public class TotalNutrients {
    @SerializedName("ENERC_KCAL")
    public TotalNutrient energy;


    @SerializedName("CHOCDF")
    public TotalNutrient carbohydrates;

    @SerializedName("SUGAR")
    public TotalNutrient sugars;

    @SerializedName("FIBTG")
    public TotalNutrient fiber;


    @SerializedName("FAT")
    public TotalNutrient fat;

    @SerializedName("FASAT")
    public TotalNutrient saturated;

    @SerializedName("FAMS")
    public TotalNutrient monounsaturated;

    @SerializedName("FAPU")
    public TotalNutrient polyunsaturated;

    @SerializedName("FATRN")
    public TotalNutrient trans;


    @SerializedName("PROCNT")
    public TotalNutrient protein;


    @SerializedName("CHOLE")
    public TotalNutrient cholesterol;


    @SerializedName("CA")
    public TotalNutrient calcium;

    @SerializedName("P")
    public TotalNutrient phosphorus;

    @SerializedName("K")
    public TotalNutrient potassium;

    @SerializedName("NA")
    public TotalNutrient sodium;

    @SerializedName("MG")
    public TotalNutrient magnesium;

    @SerializedName("FE")
    public TotalNutrient iron;


    @SerializedName("VITA_RAE")
    public TotalNutrient vitamin_a;

    @SerializedName("THIA")
    public TotalNutrient thiamin_b1;

    @SerializedName("RIBF")
    public TotalNutrient riboflavin_b2;

    @SerializedName("NIA")
    public TotalNutrient niacin_b3;

    @SerializedName("VITB6A")
    public TotalNutrient vitamin_b6;

    @SerializedName("FOLDFE")
    public TotalNutrient folate_equivalent; // Vitamin B6

    @SerializedName("VITB12")
    public TotalNutrient vitamin_b12;

    @SerializedName("VITC")
    public TotalNutrient vitamin_c;

    @SerializedName("VITD")
    public TotalNutrient vitamin_d;

    @SerializedName("TOCPHA")
    public TotalNutrient vitamin_e;

    @SerializedName("VITK1")
    public TotalNutrient vitamin_k;

    @NonNull
    @Override
    public String toString() {
        return "TotalNutrients{" +
                "calcium=" + calcium +
                ", energy=" + energy +
                ", carbs=" + carbohydrates +
                ", niacin_b3=" + niacin_b3 +
                ", cholesterol=" + cholesterol +
                ", phosphorus=" + phosphorus +
                ", monounsaturated=" + monounsaturated +
                ", protein=" + protein +
                ", polyunsaturated=" + polyunsaturated +
                ", riboflavin_b2=" + riboflavin_b2 +
                ", saturated=" + saturated +
                ", sugars=" + sugars +
                ", fat=" + fat +
                ", thiamin_b1=" + thiamin_b1 +
                ", trans=" + trans +
                ", vitamin_e=" + vitamin_e +
                ", iron_mg=" + iron +
                ", vitamin_a=" + vitamin_a +
                ", fiber=" + fiber +
                ", vitamin_b12=" + vitamin_b12 +
                ", folate_equivalent=" + folate_equivalent +
                ", vitamin_b6=" + vitamin_b6 +
                ", potassium=" + potassium +
                ", vitamin_c=" + vitamin_c +
                ", magnesium=" + magnesium +
                ", vitamin_d=" + vitamin_d +
                ", sodium=" + sodium +
                ", vitamin_k=" + vitamin_k +
                '}';
    }
}
