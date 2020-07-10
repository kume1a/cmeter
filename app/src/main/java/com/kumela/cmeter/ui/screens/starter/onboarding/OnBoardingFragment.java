package com.kumela.cmeter.ui.screens.starter.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kumela.cmeter.ui.common.base.BaseFragment;

public class OnBoardingFragment extends BaseFragment implements OnBoardingViewMvc.Listener {

    private OnBoardingViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(OnBoardingViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.setPagerAdapter((AppCompatActivity) requireActivity());
        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onNextClick() {
        mViewMvc.onPageChanged();
        mViewMvc.nextPage();
    }

    @Override
    public void onPageChanged() {
        mViewMvc.onPageChanged();
    }

    @Override
    public void onBackClick() {
        mViewMvc.onPageChanged();
        mViewMvc.previousPage();
    }

    @Override
    public void onFinished() {
        Toast.makeText(getContext(), "FINISHED", Toast.LENGTH_SHORT).show();
    }
}