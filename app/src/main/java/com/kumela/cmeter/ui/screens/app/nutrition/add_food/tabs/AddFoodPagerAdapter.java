package com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment.TabFragment;

/**
 * Created by Toko on 30,June,2020
 **/

public class AddFoodPagerAdapter extends FragmentStateAdapter {

    public AddFoodPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        TabFragment.TabType tabType = null;
        switch (position) {
            case 0:
                tabType = TabFragment.TabType.RECENT;
                break;
            case 1:
                tabType = TabFragment.TabType.FAVORITES;
                break;
        }
        if (tabType == null) throw new RuntimeException();

        Bundle bundle = new Bundle();
        bundle.putSerializable(TabFragment.EXTRA_TAB_TYPE, tabType);

        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);

        return tabFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
