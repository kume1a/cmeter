package com.kumela.cmeter.model.nutrition;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 20,June,2020
 **/

public class Tags {
    public String item;
    public String measure;
    public float quantity;

    @SerializedName("food_group")
    public int foodGroup;

    @SerializedName("tag_id")
    public int tagId;

    @NonNull
    @Override
    public String toString() {
        return "Tags{" +
                "item='" + item + '\'' +
                ", measure='" + measure + '\'' +
                ", quantity=" + quantity +
                ", foodGroup=" + foodGroup +
                ", tagId=" + tagId +
                '}';
    }
}
