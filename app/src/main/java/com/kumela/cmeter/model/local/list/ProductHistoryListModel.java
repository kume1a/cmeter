package com.kumela.cmeter.model.local.list;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.firebase.FirebaseProductHistoryItem;

import java.util.Objects;

/**
 * Created by Toko on 07,August,2020
 **/

public class ProductHistoryListModel {
    private String foodId;
    private String foodName;
    private float quantity;
    private String measureUnit;
    private float calories;
    private boolean favorite;
    private String date;

    public ProductHistoryListModel(FirebaseProductHistoryItem firebaseProductHistoryItem) {
        this.foodId = firebaseProductHistoryItem.foodId;
        this.foodName = firebaseProductHistoryItem.foodName;
        this.quantity = firebaseProductHistoryItem.quantity;
        this.measureUnit = firebaseProductHistoryItem.measureUnit;
        this.calories = firebaseProductHistoryItem.calories;
        this.favorite = firebaseProductHistoryItem.favorite;
        this.date = firebaseProductHistoryItem.date;
    }

    // --- GETTER ---
    public String getFoodId() { return foodId; }
    public String getFoodName() { return foodName; }
    public float getQuantity() { return quantity; }
    public String getMeasureUnit() { return measureUnit; }
    public float getCalories() { return calories; }
    public boolean isFavorite() { return favorite; }
    public String getDate() { return date; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductHistoryListModel that = (ProductHistoryListModel) o;
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
        return "ProductHistoryListModel{" +
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
