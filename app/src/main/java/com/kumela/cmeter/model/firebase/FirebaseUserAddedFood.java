package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.local.FoodNutrients;

import java.util.List;

/**
 * Created by Toko on 04,August,2020
 * <p>
 * used only few days in Firebase FireStore and then deleted.
 **/

public class FirebaseUserAddedFood {
    public static final String UID = "uid";
    public static final String DATE = "date";
    public static final String FAVORITE = "favorite";

    public FoodNutrients foodNutrients;
    public List<Measure> measures;

    public String uid;
    public String foodId;
    public String measureUri;
    public String meal;
    public boolean favorite;
    public String date;

    @SuppressWarnings("unused")
    public FirebaseUserAddedFood() {
    }

    public FirebaseUserAddedFood(FoodNutrients foodNutrients,
                                 List<Measure> measures,
                                 String uid,
                                 String foodId,
                                 String meal,
                                 String measureUri,
                                 boolean favorite,
                                 String date) {
        this.foodNutrients = foodNutrients;
        this.measures = measures;
        this.uid = uid;
        this.foodId = foodId;
        this.meal = meal;
        this.measureUri = measureUri;
        this.favorite = favorite;
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "FirebaseAddedFood{" +
                ", uid='" + uid + '\'' +
                ", foodId='" + foodId + '\'' +
                ", meal='" + meal + '\'' +
                ", favorite=" + favorite +
                ", date='" + date + '\'' +
                '}';
    }
}
