package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.info;

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

public class InfoFragment extends BaseFragment implements InfoViewMvc.Listener {

    private InfoViewMvc mViewMvc;
    private OnBoardingViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(InfoViewMvc.class, container);
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
    public void onAgeValueChange(int newValue) {
        mViewModel.setAge(newValue);
    }

    @Override
    public void onHeightValueChange(int newValue) {
        mViewModel.setHeight(newValue);
    }

    @Override
    public void onMaleClick() {
        mViewModel.setGender(OnBoardingViewModel.Gender.MALE);
    }

    @Override
    public void onFemaleClick() {
        mViewModel.setGender(OnBoardingViewModel.Gender.FEMALE);
    }
}
