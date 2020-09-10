package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

import com.kumela.cmeter.ui.common.nav.FragmentNavController;

/**
 * Created by Toko on 29,June,2020
 **/

public class NutritionHomeNavController extends FragmentNavController {

    public NutritionHomeNavController(View view) {
        setNavController(view);
    }

    public void actionToAddFood(@NonNull String title, @NonNull String meal, float fabX, float fabY) {
        NavDirections navDirections = NutritionHomeFragmentDirections.actionNavNutritionToAddFoodFragment(title, meal, fabX, fabY);
        getNavController().navigate(navDirections);
    }

    public void actionToMeal(@NonNull String title, @NonNull String mealType, int goalCaloriesInDay) {
        NutritionHomeFragmentDirections.ActionNavNutritionToMealFragment
                navDirections = NutritionHomeFragmentDirections.actionNavNutritionToMealFragment(title, mealType, goalCaloriesInDay);
        getNavController().navigate(navDirections);
    }
}