package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.view.View;

import androidx.navigation.NavDirections;

import com.kumela.cmeter.ui.common.nav.FragmentNavController;

/**
 * Created by Toko on 10,July,2020
 **/

public class AddFoodNavController extends FragmentNavController {

    public AddFoodNavController(View v) {
        setNavController(v);
    }

    void actionToSearch(String title, String meal, String suggestionName) {
        NavDirections navDirections =
                AddFoodFragmentDirections.actionAddFoodFragmentToSearchFragment(title, meal, suggestionName);

        getNavController().navigate(navDirections);
    }
}
