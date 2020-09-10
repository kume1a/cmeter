package com.kumela.cmeter.model.api.nutrients;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Toko on 28,July,2020
 **/

public class Ingredients {
    public List<ParsedIngredients> parsed;

    @NonNull
    @Override
    public String toString() {
        return "Ingredients{" +
                "parsed=" + parsed +
                '}';
    }
}
