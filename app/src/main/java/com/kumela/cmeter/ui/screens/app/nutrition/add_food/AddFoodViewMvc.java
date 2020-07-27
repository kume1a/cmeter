package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.view.View;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.kumela.cmeter.ui.common.mvc.ViewMvc;

/**
 * Created by Toko on 10,July,2020
 **/

public interface AddFoodViewMvc extends ViewMvc {
    int[] getSearchPosition(View view);

    void setupViewPager(FragmentManager supportFragmentManager, Lifecycle lifecycle, String meal);

    void setupToolbar(FragmentActivity activity, @StringRes int title);

    void startCircularRevealAnimation(int cx, int cy);

    void startCircularRevealExitAnimation(int endX, int endY);
}
