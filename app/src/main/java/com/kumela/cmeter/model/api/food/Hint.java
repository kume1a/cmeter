package com.kumela.cmeter.model.api.food;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Toko on 27,July,2020
 **/

public class Hint {
    public Food food;
    public List<Measure> measures;

    @NonNull
    @Override
    public String toString() {
        return "Hint{" +
                "food=" + food +
                ", measures=" + measures +
                '}';
    }
}
