package com.kumela.cmeter.model.local.fragment_models;

import androidx.annotation.NonNull;

import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.api.food.Nutrients;
import com.kumela.cmeter.model.firebase.FirebaseUserAddedFood;
import com.kumela.cmeter.model.firebase.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Toko on 15,July,2020
 **/

public class NutritionHomeModel {
    // DashBoard
    public int goalCalories;
    public int currentCalories;

    public int goalCarbohydrates;
    public int goalFats;
    public int goalProteins;

    public int carbohydrates;
    public int fats;
    public int proteins;

    // Weight
    public int goalWeight;
    public int currentWeight;

    // Meal cards
//    public List<FirebaseFoodNutrients> mFirebaseFoodsOnBreakfastModel = new ArrayList<>();
//    public List<FirebaseFoodNutrients> mFirebaseFoodsOnDinnerModel = new ArrayList<>();
//    public List<FirebaseFoodNutrients> mFirebaseFoodsOnSupperModel = new ArrayList<>();
//    public List<FirebaseFoodNutrients> mFirebaseFoodsOnSnackModels = new ArrayList<>();

    public int breakfastProgress;
    public int dinnerProgress;
    public int supperProgress;
    public int snacksProgress;

    public int[] currentBreakfastMacros = new int[3];
    public int[] currentDinnerMacros = new int[3];
    public int[] currentSupperMacros = new int[3];
    public int[] currentSnacksMacros = new int[3];


    public NutritionHomeModel(@NonNull List<FirebaseUserAddedFood> firebaseUserAddedFoods,
                              @NonNull FirebaseUser firebaseUser) {
        this.goalCalories = firebaseUser.bmr + firebaseUser.dailyExtraCalories;

        this.goalCarbohydrates = firebaseUser.carbohydrates;
        this.goalFats = firebaseUser.fats;
        this.goalProteins = firebaseUser.proteins;

        this.goalWeight = firebaseUser.goalWeight;
        this.currentWeight = firebaseUser.currentWeight;

        /*
        final float breakfastRatio = .3f;
        final float dinnerRatio = .4f;
        final float supperRatio = .25f;
        final float snacksRatio = .05f;
        
        this.goalBreakfast = (int) (goalCalories * breakfastRatio);
        this.goalDinner = (int) (goalCalories * dinnerRatio);
        this.goalSupper = (int) (goalCalories * supperRatio);
        this.goalSnacks = (int) (goalCalories * snacksRatio);
        
        final float carbohydratesRatio = .5f, carbohydratesToCalories = 4;
        final float fatsRatio = .5f, fatsToCalories = 9;
        final float proteinsRatio = .5f, proteinsToCalories = 4;
        
        this.goalBreakfastMacros[0] = (int) (goalBreakfast * carbohydratesRatio / carbohydratesToCalories);
        this.goalBreakfastMacros[1] = (int) (goalBreakfast * fatsRatio / fatsToCalories);
        this.goalBreakfastMacros[2] = (int) (goalBreakfast * proteinsRatio / proteinsToCalories);

        this.goalDinnerMacros[0] = (int) (goalDinner * carbohydratesRatio / carbohydratesToCalories);
        this.goalDinnerMacros[1] = (int) (goalDinner * fatsRatio / fatsToCalories);
        this.goalDinnerMacros[2] = (int) (goalDinner * proteinsRatio / proteinsToCalories);

        this.goalSupperMacros[0] = (int) (goalSupper * carbohydratesRatio / carbohydratesToCalories);
        this.goalSupperMacros[1] = (int) (goalSupper * fatsRatio / fatsToCalories);
        this.goalSupperMacros[2] = (int) (goalSupper * proteinsRatio / proteinsToCalories);

        this.goalSnacksMacros[0] = (int) (goalSnacks * carbohydratesRatio / carbohydratesToCalories);
        this.goalSnacksMacros[1] = (int) (goalSnacks * fatsRatio / fatsToCalories);
        this.goalSnacksMacros[2] = (int) (goalSnacks * proteinsRatio / proteinsToCalories);*/

        for (FirebaseUserAddedFood firebaseUserAddedFood : firebaseUserAddedFoods) {
            @SuppressWarnings("ConstantConditions")
            Nutrients nutrients = firebaseUserAddedFood.foodNutrients.getNutrients();

            this.currentCalories += nutrients.calories;

            this.carbohydrates += nutrients.carbohydrates;
            this.fats += nutrients.fats;
            this.proteins += nutrients.proteins;

            switch (firebaseUserAddedFood.meal) {
                case Constants.BREAKFAST:
                    this.breakfastProgress += nutrients.calories;

                    this.currentBreakfastMacros[0] += nutrients.carbohydrates;
                    this.currentBreakfastMacros[1] += nutrients.fats;
                    this.currentBreakfastMacros[2] += nutrients.proteins;
                    break;
                case Constants.DINNER:
                    this.dinnerProgress += nutrients.calories;

                    this.currentDinnerMacros[0] += nutrients.carbohydrates;
                    this.currentDinnerMacros[1] += nutrients.fats;
                    this.currentDinnerMacros[2] += nutrients.proteins;
                    break;
                case Constants.SUPPER:
                    this.supperProgress += nutrients.calories;

                    this.currentSupperMacros[0] += nutrients.carbohydrates;
                    this.currentSupperMacros[1] += nutrients.fats;
                    this.currentSupperMacros[2] += nutrients.proteins;
                    break;
                case Constants.SNACKS:
                    this.snacksProgress += nutrients.calories;

                    this.currentSnacksMacros[0] += nutrients.carbohydrates;
                    this.currentSnacksMacros[1] += nutrients.fats;
                    this.currentSnacksMacros[2] += nutrients.proteins;
                    break;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "NutritionHomeModel{" + "\n" +
                "goalCalories=" + goalCalories + "\n" +
                "currentCalories=" + currentCalories + "\n" +
                "goalCarbohydrates=" + goalCarbohydrates + "\n" +
                "goalFats=" + goalFats + "\n" +
                "goalProteins=" + goalProteins + "\n" +
                "currentCarbohydrates=" + carbohydrates + "\n" +
                "currentFats=" + fats + "\n" +
                "currentProteins=" + proteins + "\n" +
                "breakfastProgress=" + breakfastProgress + "\n" +
                "dinnerProgress=" + dinnerProgress + "\n" +
                "supperProgress=" + supperProgress + "\n" +
                "snacksProgress=" + snacksProgress + "\n" +
                "currentBreakfastMacros=" + Arrays.toString(currentBreakfastMacros) + "\n" +
                "currentDinnerMacros=" + Arrays.toString(currentDinnerMacros) + "\n" +
                "currentSupperMacros=" + Arrays.toString(currentSupperMacros) + "\n" +
                "currentSnacksMacros=" + Arrays.toString(currentSnacksMacros) + "\n" +
                '}';
    }
}
