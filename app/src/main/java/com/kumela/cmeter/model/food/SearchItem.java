package com.kumela.cmeter.model.food;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.kumela.cmeter.model.common.Photo;

/**
 * Created by Toko on 21,June,2020
 **/

public class SearchItem {
    @SerializedName("food_name")
    public String foodName;

    @SerializedName("serving_unit")
    public String servingUnit;

    @SerializedName("serving_qty")
    public int servingQuantity;

//    @SerializedName("tag_id")
//    public int tagId;

    public Photo photo;
//    public String locale;


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
}
