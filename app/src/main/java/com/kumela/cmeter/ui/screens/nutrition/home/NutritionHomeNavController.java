package com.kumela.cmeter.ui.screens.nutrition.home;

import android.content.Context;
import android.content.Intent;

import com.kumela.cmeter.ui.screens.nutrition.add_food.AddFoodActivity;
import com.kumela.cmeter.ui.common.ContextWrapper;

/**
 * Created by Toko on 29,June,2020
 **/

public class NutritionHomeNavController implements ContextWrapper {

    private Context mContext;

    public NutritionHomeNavController(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public void actionToAddFood(String title) {
        final Intent intent = new Intent(mContext, AddFoodActivity.class);
        intent.putExtra(AddFoodActivity.EXTRA_ADD_FOOD_TITLE, title);
        mContext.startActivity(intent);
    }
}