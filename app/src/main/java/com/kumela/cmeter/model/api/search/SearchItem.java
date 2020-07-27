package com.kumela.cmeter.model.api.search;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

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

    @SerializedName("tag_id")
    public int tagId;

    public SearchItem(String foodName, String servingUnit, float servingQuantity, int tagId) {
        this.foodName = foodName;
        this.servingUnit = servingUnit;
        this.servingQuantity = servingQuantity;
        this.tagId = tagId;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchItem{" +
                "foodName='" + foodName + '\'' +
                ", servingUnit='" + servingUnit + '\'' +
                ", servingQuantity=" + servingQuantity +
                ", tagId=" + tagId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchItem that = (SearchItem) o;
        return tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId);
    }
}