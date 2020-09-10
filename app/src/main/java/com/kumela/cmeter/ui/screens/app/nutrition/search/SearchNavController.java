package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.ui.common.nav.FragmentNavController;

/**
 * Created by Toko on 02,July,2020
 **/

public class SearchNavController extends FragmentNavController {

    public SearchNavController(View v) {
        setNavController(v);
    }

    public void actionToFoodDetails(@NonNull String foodId,
                                    @NonNull String foodName,
                                    @NonNull String meal,
                                    @NonNull Measure[] measures) {
        NavDirections navDirections =
                SearchFragmentDirections.actionSearchFragmentToNutritionDetailsFragment(
                        foodId, foodName, meal, measures
                );

        getNavController().navigate(navDirections);
    }
}
