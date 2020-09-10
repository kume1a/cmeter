package com.kumela.cmeter.model.api.food;

import androidx.annotation.NonNull;

/**
 * Created by Toko on 27,July,2020
 **/

public class Parsed {
    public Food mFood;

    @NonNull
    @Override
    public String toString() {
        return "Parsed{" +
                "mFood=" + mFood +
                '}';
    }
}
