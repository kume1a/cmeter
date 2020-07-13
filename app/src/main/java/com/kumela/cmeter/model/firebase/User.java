package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

/**
 * Created by Toko on 13,July,2020
 **/

public class User {
    public String uid;

    public String username;
    public String email;

    public Integer bmr;
    public Integer dailyExtraCalories;
    public Integer dailyWaterIntake;

    public User() {
    }

    public User(String uid, String username, String email, Integer bmr, Integer dailyExtraCalories, Integer dailyWaterIntake) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.bmr = bmr;
        this.dailyExtraCalories = dailyExtraCalories;
        this.dailyWaterIntake = dailyWaterIntake;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", bmr=" + bmr +
                ", extraCalories=" + dailyExtraCalories +
                ", dailyWaterIntake=" + dailyWaterIntake +
                '}';
    }
}
