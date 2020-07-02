package com.kumela.cmeter.ui.screens.nutrition.add_food;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.screens.nutrition.search.SearchActivity;
import com.kumela.cmeter.ui.common.mvc.BaseViewMvc;

/**
 * Created by Toko on 26,June,2020
 **/

public class AddFoodMvc extends BaseViewMvc {

    public AddFoodMvc(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.add_food_activity, container, false));
    }

    void init() {
    }

    public boolean optionItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_food_search) {

            // get x and y of search item from menu
            View menuView = findViewById(R.id.menu_add_food_search);
            int[] location = new int[2];
            menuView.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];

            // put x and y values to the intent (for circular reveal animation)
            Intent intent = new Intent(getContext(), SearchActivity.class);
            intent.putExtra(SearchActivity.EXTRA_SEARCH_X, x);
            intent.putExtra(SearchActivity.EXTRA_SEARCH_Y, y);

            getContext().startActivity(intent);
            return true;
        }
        return false;
    }
}
