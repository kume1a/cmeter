package com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment;

import android.content.Context;
import android.content.Intent;

import com.kumela.cmeter.ui.common.nav.ActivityNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsActivity;

/**
 * Created by Toko on 23,July,2020
 **/

public class TabNavController extends ActivityNavController {

    public TabNavController(Context context) {
        setContext(context);
    }

    public void actionToFoodDetails(String foodName, String mealType) {
        Intent intent = new Intent(getContext(), NutritionDetailsActivity.class);
        intent.putExtra(NutritionDetailsActivity.EXTRA_FOOD_NAME, foodName);
        intent.putExtra(NutritionDetailsActivity.EXTRA_MEAL_TYPE, mealType);
        startActivity(intent);
    }
}
