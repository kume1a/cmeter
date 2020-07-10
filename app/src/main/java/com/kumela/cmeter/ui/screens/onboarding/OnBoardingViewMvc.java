package com.kumela.cmeter.ui.screens.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 09,July,2020
 **/

public interface OnBoardingViewMvc extends BaseObservableViewMvc<OnBoardingViewMvc.Listener> {
    void nextPage();

    void previousPage();

    void onPageChanged();

    interface Listener {
        void onNextClick();

        void onBackClick();

        void onFinished();

        void onPageChanged();
    }

    void setPagerAdapter(AppCompatActivity activity);
}
