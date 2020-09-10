package com.kumela.cmeter.model.api.food;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Toko on 27,July,2020
 **/

public class FoodResponseSchema {
    public List<Parsed> parsed;
    public List<Hint> hints;

    @NonNull
    @Override
    public String toString() {
        return "FoodApiResponse{" + "\n" +
                "parsed = " + parsed + "\n" +
                "hints = " + hints + "\n" +
                '}';
    }
}
