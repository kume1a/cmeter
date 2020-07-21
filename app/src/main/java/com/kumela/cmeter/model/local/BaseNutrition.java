package com.kumela.cmeter.model.local;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Created by Toko on 06,July,2020
 **/

public class BaseNutrition {
    private float calories;
    private float carbohydrates;
    private float proteins;
    private float fats;

    public BaseNutrition(float calories, float carbohydrates, float fats, float proteins) {
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;
    }

    public BaseNutrition() {}

    public float getCalories() { return calories; }
    public float getCarbohydrates() { return carbohydrates; }
    public float getProteins() { return proteins; }
    public float getFats() { return fats; }

    @NonNull
    @Override
    public String toString() {
        return "BaseNutrition{" +
                "calories='" + calories + '\'' +
                ", carbohydrates='" + carbohydrates + '\'' +
                ", proteins='" + proteins + '\'' +
                ", fats='" + fats + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseNutrition that = (BaseNutrition) o;
        return Objects.equals(calories, that.calories) &&
                Objects.equals(carbohydrates, that.carbohydrates) &&
                Objects.equals(proteins, that.proteins) &&
                Objects.equals(fats, that.fats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, carbohydrates, proteins, fats);
    }
}
