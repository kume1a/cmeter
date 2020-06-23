package com.kumela.cmeter.model.common;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by Toko on 20,June,2020
 **/

public class Photo {
    @SerializedName("thumb")
    public String thumbnail;

    @SerializedName("highres")
    public String highRes;

    @SerializedName("is_user_uploaded")
    public boolean isUserUploaded;

    @NonNull
    @Override
    public String toString() {
        return "Photo{" +
                "thumbnail='" + thumbnail + '\'' +
                ", highRes='" + highRes + '\'' +
                ", isUserUploaded=" + isUserUploaded +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return isUserUploaded == photo.isUserUploaded &&
                Objects.equals(thumbnail, photo.thumbnail) &&
                Objects.equals(highRes, photo.highRes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thumbnail, highRes, isUserUploaded);
    }
}
