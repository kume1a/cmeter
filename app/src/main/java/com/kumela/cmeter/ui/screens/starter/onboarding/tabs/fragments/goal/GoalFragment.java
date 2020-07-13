package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.goal;

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

public class GoalFragment extends BaseFragment implements GoalViewMvc.Listener {

    private OnBoardingViewModel mViewModel;
    private GoalViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(GoalViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(
                requireActivity(),
                getViewModelFactory()
        ).get(OnBoardingViewModel.class);
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
    public void onLoseWeightClick() {
        mViewModel.setGoal(OnBoardingViewModel.Goal.LOSE_WEIGHT);
    }

    @Override
    public void onMaintainWeightClick() {
        mViewModel.setGoal(OnBoardingViewModel.Goal.MAINTAIN_WEIGHT);
    }

    @Override
    public void onGainWeightClick() {
        mViewModel.setGoal(OnBoardingViewModel.Goal.GAIN_WEIGHT);
    }
}
