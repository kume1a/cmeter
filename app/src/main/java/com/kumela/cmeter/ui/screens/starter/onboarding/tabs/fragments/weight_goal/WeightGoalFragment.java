package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal;

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

public class WeightGoalFragment extends BaseFragment implements WeightGoalViewMvc.Listener {

    private WeightGoalViewMvc mViewMvc;
    private OnBoardingViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(WeightGoalViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity(), getViewModelFactory()).get(OnBoardingViewModel.class);

        mViewModel.getGoalLiveData().observe(getViewLifecycleOwner(), goal -> {
            switch (goal) {
                case LOSE_WEIGHT:
                    mViewMvc.onGoalChangedToLoseWeight();
                    break;
                case MAINTAIN_WEIGHT:
                    mViewMvc.onGoalChangedToMaintainWeight();
                    break;
                case GAIN_WEIGHT:
                    mViewMvc.onGoalChangedToGainWeight();
                    break;
            }
        });
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
    public void on25CLick() {
        mViewModel.setWeightChangeGoal(OnBoardingViewModel.WeightDiff.DIFF_25);
    }

    @Override
    public void on50CLick() {
        mViewModel.setWeightChangeGoal(OnBoardingViewModel.WeightDiff.DIFF_50);
    }

    @Override
    public void on75CLick() {
        mViewModel.setWeightChangeGoal(OnBoardingViewModel.WeightDiff.DIFF_75);
    }

    @Override
    public void onCurrentWeightValueChanged(int newValue) {
        mViewModel.setCurrentWeight(newValue);
    }

    @Override
    public void onGoalWeightValueChanged(int newValue) {
        mViewModel.setGoalWeight(newValue);
    }
}
