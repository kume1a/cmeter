package com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment;

import android.view.View;
import android.widget.Toast;

import com.kumela.cmeter.ui.common.nav.FragmentNavController;

/**
 * Created by Toko on 23,July,2020
 **/

public class TabNavController extends FragmentNavController {

    private View v;

    public TabNavController(View v) {
        setNavController(v);
        this.v = v;
    }

    public void actionToFoodDetails(String foodName, String mealType) {
        Toast.makeText(v.getContext(), "TODO IMPLEMENT NAVIGATION", Toast.LENGTH_LONG).show();
    }
}
