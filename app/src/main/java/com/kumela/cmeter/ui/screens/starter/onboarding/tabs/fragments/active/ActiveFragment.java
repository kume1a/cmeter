package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.active;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.ui.common.base.BaseFragment;
import com.kumela.cmeter.ui.screens.starter.onboarding.OnBoardingViewModel;

/**
 * Created by Toko on 09,July,2020
 **/

public class ActiveFragment extends BaseFragment implements ActiveViewMvc.Listener {

    private OnBoardingViewModel mViewModel;
    private ActiveViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(ActiveViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity(), getViewModelFactory()).get(OnBoardingViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onNoActivityClick() {
        mViewModel.setActivityLevel(OnBoardingViewModel.ActivityLevel.NO_ACTIVITY);
    }

    @Override
    public void onLightActivityClick() {
        mViewModel.setActivityLevel(OnBoardingViewModel.ActivityLevel.LIGHT_ACTIVITY);
    }

    @Override
    public void onModerateActivityClick() {
        mViewModel.setActivityLevel(OnBoardingViewModel.ActivityLevel.MODERATE_ACTIVITY);
    }

    @Override
    public void onHeavyActivityClick() {
        mViewModel.setActivityLevel(OnBoardingViewModel.ActivityLevel.HEAVY_ACTIVITY);
    }

    @Override
    public void onVeryHeavyActivityClick() {
        mViewModel.setActivityLevel(OnBoardingViewModel.ActivityLevel.VERY_HEAVY_ACTIVITY);
    }
}
