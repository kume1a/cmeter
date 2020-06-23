package com.kumela.cmeter.model.nutrition;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 20,June,2020
 **/

public class MetaData {
    @SerializedName("is_raw_food")
    public boolean isRawFood;

    @NonNull
    @Override
    public String toString() {
        return "MetaData{" +
                "isRawFood=" + isRawFood +
                '}';
    }
}
