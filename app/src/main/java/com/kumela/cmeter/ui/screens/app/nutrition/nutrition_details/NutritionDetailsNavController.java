package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.view.View;

import com.kumela.cmeter.ui.common.nav.FragmentNavController;

/**
 * Created by Toko on 03,August,2020
 **/

public class NutritionDetailsNavController extends FragmentNavController {
    public NutritionDetailsNavController(View v) {
        setNavController(v);
    }

    void actionBack() {
        getNavController().popBackStack();
    }
}
