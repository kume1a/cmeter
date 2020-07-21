package com.kumela.cmeter.model.api.nutrition;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by Toko on 20,June,2020
 **/

public class AltMeasure {

    @SerializedName("serving_weight")
    public float servingWeight;

    public String measure;
    public int seq;
    public float qty;

    @SuppressWarnings("unused")
    public AltMeasure() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AltMeasure that = (AltMeasure) o;
        return Objects.equals(measure, that.measure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(measure);
    }

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
