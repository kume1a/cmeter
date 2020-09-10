package com.kumela.cmeter.model.api.food;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Created by Toko on 27,July,2020
 **/

public class Measure implements Parcelable {
    public String uri;
    public String label;

    @SuppressWarnings("unused")
    public Measure() {}

    protected Measure(Parcel in) {
        uri = in.readString();
        label = in.readString();
    }

    public static final Creator<Measure> CREATOR = new Creator<Measure>() {
        @Override
        public Measure createFromParcel(Parcel in) {
            return new Measure(in);
        }

        @Override
        public Measure[] newArray(int size) {
            return new Measure[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "Measure{" +
                "uri='" + uri + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
        dest.writeString(label);
    }
}
