package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.content.Context;
import android.content.Intent;

import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.ui.common.nav.ActivityNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsActivity;

/**
 * Created by Toko on 02,July,2020
 **/

public class SearchNavController extends ActivityNavController {

    public SearchNavController(Context context) {
        setContext(context);
    }

    public void actionToFoodDetails(String foodName, String mealType) {
        Intent intent = new Intent(getContext(), NutritionDetailsActivity.class);
        intent.putExtra(NutritionDetailsActivity.EXTRA_FOOD_NAME, foodName);
        intent.putExtra(NutritionDetailsActivity.EXTRA_MEAL_TYPE, mealType);
        startActivity(intent);
    }
}
