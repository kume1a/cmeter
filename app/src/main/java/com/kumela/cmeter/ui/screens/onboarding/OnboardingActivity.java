package com.kumela.cmeter.ui.screens.onboarding;

import android.os.Bundle;
import android.widget.Toast;

import com.kumela.cmeter.ui.common.base.BaseActivity;

public class OnboardingActivity extends BaseActivity implements OnBoardingViewMvc.Listener {

    private OnBoardingViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getViewMvcFactory().newInstance(OnBoardingViewMvc.class, null);
        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.setPagerAdapter(this);

        mViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
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
        Toast.makeText(this, "FINISHED", Toast.LENGTH_SHORT).show();
    }
}