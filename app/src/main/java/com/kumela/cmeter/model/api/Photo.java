package com.kumela.cmeter.model.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by Toko on 20,June,2020
 **/

public class Photo {
    @Nullable
    @SerializedName("highres")
    public String highRes;

    @NonNull
    @Override
    public String toString() {
        return "Photo{" +
                ", highRes='" + highRes + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(highRes, photo.highRes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(highRes);
    }
}
