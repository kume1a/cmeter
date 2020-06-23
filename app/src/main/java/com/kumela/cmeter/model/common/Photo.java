package com.kumela.cmeter.model.common;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

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
}
