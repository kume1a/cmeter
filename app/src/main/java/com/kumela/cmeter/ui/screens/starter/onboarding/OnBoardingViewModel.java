package com.kumela.cmeter.ui.screens.starter.onboarding;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.firebase.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Toko on 09,July,2020
 **/

public class OnBoardingViewModel extends ViewModel {

    public enum Goal {
        LOSE_WEIGHT, MAINTAIN_WEIGHT, GAIN_WEIGHT
    }

    public enum ActivityLevel {
        NO_ACTIVITY, LIGHT_ACTIVITY, MODERATE_ACTIVITY, HEAVY_ACTIVITY, VERY_HEAVY_ACTIVITY
    }

    public enum WeightDiff {
        DIFF_25, DIFF_50, DIFF_75
    }

    public enum Gender {
        MALE, FEMALE
    }

    private MutableLiveData<Goal> mGoal = new MutableLiveData<>(Goal.MAINTAIN_WEIGHT);
    private ActivityLevel mActivityLevel = ActivityLevel.NO_ACTIVITY;
    private Gender mGender = Gender.MALE;

    private float mWeightChangeGoal = 0;

    private int mCurrentWeight = Constants.AVERAGE_WEIGHT;
    private int mGoalWeight = Constants.AVERAGE_WEIGHT;

    private int mAge = Constants.AVERAGE_AGE;
    private int mHeight = Constants.AVERAGE_HEIGHT;


    private DatabaseReference mDatabase;
    private String mUserId;

    public OnBoardingViewModel(FirebaseDatabase firebaseDatabase, String uid) {
        mDatabase = firebaseDatabase.getReference();
        mUserId = uid;
    }

    public LiveData<Goal> getGoalLiveData() {
        return mGoal;
    }

    public void setGoal(@NonNull Goal goal) {
        mGoal.setValue(goal);

        switch (goal) {
            case LOSE_WEIGHT:
                if (mWeightChangeGoal > 0) mWeightChangeGoal = -mWeightChangeGoal;
                else if (mWeightChangeGoal == 0) mWeightChangeGoal = -.25f;
                break;
            case MAINTAIN_WEIGHT:
                if (mWeightChangeGoal != 0) mWeightChangeGoal = 0;
                break;
            case GAIN_WEIGHT:
                if (mWeightChangeGoal < 0) mWeightChangeGoal = -mWeightChangeGoal;
                else if (mWeightChangeGoal == 0) mWeightChangeGoal = .25f;
                break;
        }
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        mActivityLevel = activityLevel;
    }

    public void setWeightChangeGoal(WeightDiff weightDifference) {
        if (mGoal.getValue() == Goal.LOSE_WEIGHT) {
            switch (weightDifference) {
                case DIFF_25:
                    mWeightChangeGoal = -.25f;
                    break;
                case DIFF_50:
                    mWeightChangeGoal = -.5f;
                    break;
                case DIFF_75:
                    mWeightChangeGoal = -.75f;
                    break;
            }
        } else if (mGoal.getValue() == Goal.GAIN_WEIGHT) {
            switch (weightDifference) {
                case DIFF_25:
                    mWeightChangeGoal = .25f;
                    break;
                case DIFF_50:
                    mWeightChangeGoal = .5f;
                    break;
                case DIFF_75:
                    mWeightChangeGoal = .75f;
                    break;
            }
        } else throw new RuntimeException("setWeightChangeGoal was called with invalid Goal value");
    }

    public void setGender(Gender gender) {
        mGender = gender;
    }

    public void setCurrentWeight(int currentWeight) {
        mCurrentWeight = currentWeight;
    }

    public void setGoalWeight(int goalWeight) {
        mGoalWeight = goalWeight;
    }

    public int getCurrentWeight() {
        return mCurrentWeight;
    }

    public int getGoalWeight() {
        return mGoalWeight;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getAge() {
        return mAge;
    }

    public int getHeight() {
        return mHeight;
    }

    private Map<ActivityLevel, Float> BMRMultiplier = new HashMap<ActivityLevel, Float>() {{
        put(ActivityLevel.NO_ACTIVITY, 1.2f);
        put(ActivityLevel.LIGHT_ACTIVITY, 1.375f);
        put(ActivityLevel.MODERATE_ACTIVITY, 1.55f);
        put(ActivityLevel.HEAVY_ACTIVITY, 1.725f);
        put(ActivityLevel.VERY_HEAVY_ACTIVITY, 1.9f);
    }};

    @SuppressWarnings("ConstantConditions")
    public int getBMR() {
        float bmr;
        if (mGender == Gender.MALE) {
            bmr = 88.362f + (13.397f * mCurrentWeight) + (4.799f * mHeight) - (5.677f * mAge);
        } else {
            bmr = 447.593f + (9.247f * mCurrentWeight) + (3.098f * mHeight) - (4.330f * mAge);
        }
        return (int) (bmr * BMRMultiplier.get(mActivityLevel));
    }

    public int getDailyWaterIntake() {
        float weightInPounds = mCurrentWeight * 2.20462f;
        float waterInOunces = weightInPounds * 2 / 3;
        return (int) (waterInOunces * 0.0295735f);
    }

    public int getDailyExtra() {
        // 7716.17f = calories per kg
        return (int) (mWeightChangeGoal * 7716.17f / 7f);
    }

    /* Calculation ratio
       carbohydrates : fats : protein = 1 / 2 / 2
     */
    public int getCarbohydrates(int goalCalories) {
        return (int) (goalCalories * .5f / 4f);
    }

    public int getFats(int goalCalories) {
        return (int) (goalCalories * .25f / 9f);
    }

    public int getProteins(int goalCalories) {
        return (int) (goalCalories * .25f / 4f);
    }

    // Firebase create user
    public interface Listener {
        void onDatabaseWriteCompleted();
    }

    private Set<Listener> mListeners = new HashSet<>(1);

    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    public void createUserAndNotify(User user) {
        Log.d(getClass().getSimpleName(), "createUserAndNotify: called, user = " + user);

        mDatabase.child(Constants.CHILD_USERS)
                .child(mUserId)
                .setValue(user)
                .addOnSuccessListener(aVoid -> {
                    for (Listener listener : mListeners) listener.onDatabaseWriteCompleted();
                })
                .addOnFailureListener(e -> Log.e(getClass().getSimpleName(), "createUserAndNotify: ", e));
    }

    @NonNull
    @Override
    public String toString() {
        return "OnBoardingViewModel{" +
                "mGoal=" + mGoal.getValue() +
                ", mActivityLevel=" + mActivityLevel +
                ", mWeightChangeGoal=" + mWeightChangeGoal +
                ", mGender=" + mGender +
                ", mCurrentWeight=" + mCurrentWeight +
                ", mGoalWeight=" + mGoalWeight +
                ", mAge=" + mAge +
                ", mHeight=" + mHeight +
                '}';
    }
}
