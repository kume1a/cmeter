package com.kumela.cmeter.model.local;

import androidx.annotation.NonNull;

import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.firebase.AddedFood;
import com.kumela.cmeter.model.firebase.User;

import java.util.ArrayList;
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

    public int currentCarbohydrates;
    public int currentFats;
    public int currentProteins;

    // Weight
    public int goalWeight;
    public int currentWeight;

    // Meal cards
    public List<AddedFood> addedFoodsOnBreakfast = new ArrayList<>();
    public List<AddedFood> addedFoodsOnDinner = new ArrayList<>();
    public List<AddedFood> addedFoodsOnSupper = new ArrayList<>();
    public List<AddedFood> addedFoodsOnSnacks = new ArrayList<>();

    public int breakfastProgress;
    public int dinnerProgress;
    public int supperProgress;
    public int snacksProgress;

    public int[] currentBreakfastMacros = new int[3];
    public int[] currentDinnerMacros = new int[3];
    public int[] currentSupperMacros = new int[3];
    public int[] currentSnacksMacros = new int[3];


    public NutritionHomeModel(List<AddedFood> addedFoods, User user) {
        this.goalCalories = user.bmr + user.dailyExtraCalories;

        this.goalCarbohydrates = user.carbohydrates;
        this.goalFats = user.fats;
        this.goalProteins = user.proteins;

        this.goalWeight = user.goalWeight;
        this.currentWeight = user.currentWeight;

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

        for (AddedFood addedFood : addedFoods) {
            this.currentCalories += addedFood.totalCalories;

            this.currentCarbohydrates += addedFood.totalCarbohydrates;
            this.currentFats += addedFood.totalFats;
            this.currentProteins += addedFood.totalProteins;

            switch (addedFood.mealType) {
                case Constants.BREAKFAST:
                    this.addedFoodsOnBreakfast.add(addedFood);
                    this.breakfastProgress += addedFood.totalCalories;

                    this.currentBreakfastMacros[0] += addedFood.totalCarbohydrates;
                    this.currentBreakfastMacros[1] += addedFood.totalFats;
                    this.currentBreakfastMacros[2] += addedFood.totalProteins;
                    break;
                case Constants.DINNER:
                    this.addedFoodsOnDinner.add(addedFood);
                    this.dinnerProgress += addedFood.totalCalories;

                    this.currentDinnerMacros[0] += addedFood.totalCarbohydrates;
                    this.currentDinnerMacros[1] += addedFood.totalFats;
                    this.currentDinnerMacros[2] += addedFood.totalProteins;
                    break;
                case Constants.SUPPER:
                    this.addedFoodsOnSupper.add(addedFood);
                    this.supperProgress += addedFood.totalCalories;

                    this.currentSupperMacros[0] += addedFood.totalCarbohydrates;
                    this.currentSupperMacros[1] += addedFood.totalFats;
                    this.currentSupperMacros[2] += addedFood.totalProteins;
                    break;
                case Constants.SNACKS:
                    this.addedFoodsOnSnacks.add(addedFood);
                    this.snacksProgress += addedFood.totalCalories;

                    this.currentSnacksMacros[0] += addedFood.totalCarbohydrates;
                    this.currentSnacksMacros[1] += addedFood.totalFats;
                    this.currentSnacksMacros[2] += addedFood.totalProteins;
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
                "currentCarbohydrates=" + currentCarbohydrates + "\n" +
                "currentFats=" + currentFats + "\n" +
                "currentProteins=" + currentProteins + "\n" +
                "addedFoodsOnBreakfast=" + addedFoodsOnBreakfast + "\n" +
                "addedFoodsOnDinner=" + addedFoodsOnDinner + "\n" +
                "addedFoodsOnSupper=" + addedFoodsOnSupper + "\n" +
                "addedFoodsOnSnacks=" + addedFoodsOnSnacks + "\n" +
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
