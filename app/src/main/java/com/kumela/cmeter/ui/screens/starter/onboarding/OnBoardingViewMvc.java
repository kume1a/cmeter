package com.kumela.cmeter.ui.screens.starter.onboarding;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 09,July,2020
 **/

public interface OnBoardingViewMvc extends ObservableViewMvc<OnBoardingViewMvc.Listener> {
    interface Listener {
        void onNextClick();

        void onBackClick();

        void onFinished();

        void onPageChanged();
    }

    void setPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle);

    void showErrorSnackBar(@StringRes int errorMsgId);

    void nextPage();

    void previousPage();

    void onPageChanged();
}
