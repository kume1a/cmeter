package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.content.Context;
import android.content.Intent;

import com.kumela.cmeter.ui.common.nav.ActivityNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.search.SearchActivity;

/**
 * Created by Toko on 10,July,2020
 **/

public class AddFoodNavController extends ActivityNavController {

    public AddFoodNavController(Context context) {
        setContext(context);
    }

    void actionToSearch(int x, int y) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra(SearchActivity.EXTRA_SEARCH_X, x);
        intent.putExtra(SearchActivity.EXTRA_SEARCH_Y, y);

        startActivity(intent);
    }
}
