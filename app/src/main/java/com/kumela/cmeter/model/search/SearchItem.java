package com.kumela.cmeter.model.search;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.kumela.cmeter.model.common.Photo;

import java.util.Objects;

/**
 * Created by Toko on 21,June,2020
 **/

public class SearchItem {
    @SerializedName("food_name")
    public String foodName;

    @SerializedName("serving_unit")
    public String servingUnit;

    @SerializedName("serving_qty")
    public float servingQuantity;

//    @SerializedName("tag_id")
//    public int tagId;

    public Photo photo;

    @NonNull
    @Override
    public String toString() {
        return "SearchItem{" +
                "foodName='" + foodName + '\'' +
                ", servingUnit='" + servingUnit + '\'' +
                ", servingQuantity=" + servingQuantity +
                ", photo=" + photo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchItem that = (SearchItem) o;
        return servingQuantity == that.servingQuantity &&
                Objects.equals(foodName, that.foodName) &&
                Objects.equals(servingUnit, that.servingUnit) &&
                Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodName, servingUnit, servingQuantity, photo);
    }
}
