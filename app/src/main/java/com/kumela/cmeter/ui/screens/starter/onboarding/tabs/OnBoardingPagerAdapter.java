package com.kumela.cmeter.ui.screens.starter.onboarding.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.ActiveFragment;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.GoalFragment;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal.WeightGoalFragment;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.InfoFragment;

/**
 * Created by Toko on 09,July,2020
 **/

public class OnBoardingPagerAdapter extends FragmentStateAdapter {
    public OnBoardingPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GoalFragment();
                break;
            case 1:
                fragment = new ActiveFragment();
                break;
            case 2:
                fragment = new WeightGoalFragment();
                break;
            case 3:
                fragment = new InfoFragment();
                break;
        }
        if (fragment == null) throw new RuntimeException("Fragment in view pager must not be null");
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
