package com.kumela.cmeter.ui.screens.nutrition.add_food.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kumela.cmeter.ui.screens.nutrition.add_food.tabs.fragments.FavoritesFragment;
import com.kumela.cmeter.ui.screens.nutrition.add_food.tabs.fragments.RecentFragment;

/**
 * Created by Toko on 30,June,2020
 **/

public class TabsAdapter extends FragmentStateAdapter {

    public TabsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new RecentFragment();
                break;
            case 1:
                fragment = new FavoritesFragment();
                break;
        }
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        if (fragment == null) throw new RuntimeException("Fragment in view pager must not be null");
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
