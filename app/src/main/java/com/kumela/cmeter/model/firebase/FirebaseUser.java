package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

/**
 * Created by Toko on 13,July,2020
 **/

public class FirebaseUser {
    public String username;
    public String email;

    public Integer goalWeight;
    public Integer currentWeight;
    public Integer height;
    public Integer age;

    public Integer bmr;
    public Integer dailyExtraCalories;
    public Integer dailyWaterIntake;
    public Integer carbohydrates;
    public Integer fats;
    public Integer proteins;

    @SuppressWarnings("unused")
    public FirebaseUser() {
    }

    public FirebaseUser(String username,
                        String email,
                        Integer bmr,
                        Integer dailyExtraCalories,
                        Integer dailyWaterIntake,
                        Integer carbohydrates,
                        Integer fats,
                        Integer proteins,
                        Integer goalWeight,
                        Integer currentWeight,
                        Integer height,
                        Integer age

    ) {
        this.username = username;
        this.email = email;
        this.bmr = bmr;
        this.dailyExtraCalories = dailyExtraCalories;
        this.dailyWaterIntake = dailyWaterIntake;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;

        this.goalWeight = goalWeight;
        this.currentWeight = currentWeight;
        this.height = height;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", goalWeight=" + goalWeight +
                ", currentWeight=" + currentWeight +
                ", height=" + height +
                ", age=" + age +
                ", bmr=" + bmr +
                ", dailyExtraCalories=" + dailyExtraCalories +
                ", dailyWaterIntake=" + dailyWaterIntake +
                ", carbohydrates=" + carbohydrates +
                ", fats=" + fats +
                ", proteins=" + proteins +
                '}';
    }
}
