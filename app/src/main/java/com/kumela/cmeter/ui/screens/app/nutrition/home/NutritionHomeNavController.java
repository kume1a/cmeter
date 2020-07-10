package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.view.View;

import androidx.navigation.NavDirections;

import com.kumela.cmeter.ui.common.nav.BaseNavController;

/**
 * Created by Toko on 29,June,2020
 **/

public class NutritionHomeNavController extends BaseNavController {

    public NutritionHomeNavController(View view) {
        setNavController(view);
    }

    public void actionToAddFood(String title) {
        NavDirections navDirections = NutritionHomeFragmentDirections.actionNavNutritionToAddFoodFragment(title);
        getNavController().navigate(navDirections);
    }
}