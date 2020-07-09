package com.kumela.cmeter.model.nutrition;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 20,June,2020
 **/

public class AltMeasure {

    @SerializedName("serving_weight")
    public float servingWeight;

    public String measure;
    public int seq;
    public float qty;

    @NonNull
    @Override
    public String toString() {
        return "AltMeasure{" +
                "servingWeight=" + servingWeight +
                ", measure='" + measure + '\'' +
                ", seq=" + seq +
                ", qty=" + qty +
                '}';
    }
}
