package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Created by Toko on 05,August,2020
 **/

public class FirebaseProductHistoryItem {
    public String foodId;
    public String foodName;
    public float quantity;
    public String measureUnit;
    public float calories;
    public boolean favorite;
    public String date;

    @SuppressWarnings("unused")
    public FirebaseProductHistoryItem() {
    }

    public FirebaseProductHistoryItem(String foodId,
                                      String foodName,
                                      float quantity,
                                      String measureUnit,
                                      float calories,
                                      boolean favorite,
                                      String date) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.measureUnit = measureUnit;
        this.calories = calories;
        this.favorite = favorite;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirebaseProductHistoryItem that = (FirebaseProductHistoryItem) o;
        return Objects.equals(foodId, that.foodId) &&
                Objects.equals(foodName, that.foodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodId, foodName);
    }

    @NonNull
    @Override
    public String toString() {
        return "FirebaseProductHistory{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", quantity=" + quantity +
                ", measureUnit='" + measureUnit + '\'' +
                ", calories=" + calories +
                ", favorite=" + favorite +
                ", date='" + date + '\'' +
                '}';
    }
}
