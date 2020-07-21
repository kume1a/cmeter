package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;
import com.kumela.cmeter.ui.common.mvc.BaseViewMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.AddFoodPagerAdapter;

/**
 * Created by Toko on 26,June,2020
 **/

public class AddFoodViewMvcImpl extends BaseViewMvc implements AddFoodViewMvc {

    public AddFoodViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.add_food_fragment, container, false));
    }

    @Override
    public int[] getSearchPosition(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    @Override
    public void setupViewPager(AppCompatActivity appCompatActivity) {
        ViewPager2 viewPager = findViewById(R.id.vp_add_food);
        viewPager.setAdapter(new AddFoodPagerAdapter(
                appCompatActivity.getSupportFragmentManager(),
                appCompatActivity.getLifecycle()
        ));

        TabLayout tabLayout = findViewById(R.id.tabs_add_food);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_recent);
                    tab.setText(R.string.tabs_recent);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_favorite);
                    tab.setText(R.string.tabs_favorite);
                    break;
            }
        }).attach();
    }

    @Override
    public void setupToolbar(FragmentActivity activity, @StringRes int title) {
        ((ToolbarHelper) activity).setTitle(getResources().getString(title));
    }
}
