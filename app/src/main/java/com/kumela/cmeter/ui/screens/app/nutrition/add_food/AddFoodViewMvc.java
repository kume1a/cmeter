package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.kumela.cmeter.ui.common.mvc.ViewMvc;

/**
 * Created by Toko on 10,July,2020
 **/

public interface AddFoodViewMvc extends ViewMvc {
    int[] getSearchPosition(View view);

    void setupViewPager(AppCompatActivity appCompatActivity);

    void setupToolbar(FragmentActivity activity, String title);
}
