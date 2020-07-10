package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.content.Context;
import android.content.Intent;

import com.kumela.cmeter.model.search.SearchItem;
import com.kumela.cmeter.ui.common.nav.ActivityNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Toko on 02,July,2020
 **/

public class SearchNavController extends ActivityNavController {

    public SearchNavController(Context context) {
        setContext(context);
    }

    public void actionToFoodDetails(SearchItem searchItem) {
        Intent intent = new Intent(getContext(), NutritionDetailsActivity.class);
        intent.putExtra(NutritionDetailsActivity.EXTRA_FOOD_NAME, searchItem.foodName);
        startActivity(intent);
    }
}
