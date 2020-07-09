package com.kumela.cmeter.ui.screens.nutrition.search;

import android.content.Context;
import android.content.Intent;

import com.kumela.cmeter.model.search.SearchItem;
import com.kumela.cmeter.ui.common.ContextWrapper;
import com.kumela.cmeter.ui.screens.nutrition.nutrition_details.NutritionDetailsActivity;

/**
 * Created by Toko on 02,July,2020
 **/

public class SearchNavController implements ContextWrapper {

    private Context mContext;

    public SearchNavController(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public void actionToFoodDetails(SearchItem searchItem) {
        Intent intent = new Intent(getContext(), NutritionDetailsActivity.class);
        intent.putExtra(NutritionDetailsActivity.EXTRA_FOOD_NAME, searchItem.foodName);
        getContext().startActivity(intent);
    }
}
