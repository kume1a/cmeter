package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.ui.common.base.BaseFragment;

/**
 * Created by Toko on 09,July,2020
 **/

public class WeightGoalFragment extends BaseFragment {

    private WeightGoalViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(WeightGoalViewMvc.class, container);
        return mViewMvc.getRootView();
    }
}
