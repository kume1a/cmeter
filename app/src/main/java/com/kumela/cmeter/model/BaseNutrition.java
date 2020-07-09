package com.kumela.cmeter.model;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Created by Toko on 06,July,2020
 **/

public class BaseNutrition {
    private String calories;
    private String carbohydrates;
    private String proteins;
    private String fats;

    public BaseNutrition(String calories, String carbohydrates, String fats, String proteins) {
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fats = fats;
    }

    public String getCalories() {
        return (int)Float.parseFloat(calories) + "";
    }

    public String getCarbohydrates() {
        return (int)Float.parseFloat(carbohydrates) + "";
    }

    public String getProteins() {
        return (int)Float.parseFloat(proteins) + "";
    }

    public String getFats() {
        return (int)Float.parseFloat(fats) + "";
    }

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
