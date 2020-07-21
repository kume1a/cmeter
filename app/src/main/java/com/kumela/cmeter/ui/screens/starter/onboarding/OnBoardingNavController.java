package com.kumela.cmeter.ui.screens.starter.onboarding;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.StringRes;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.nav.DualNavController;
import com.kumela.cmeter.ui.screens.app.AppActivity;

/**
 * Created by Toko on 13,July,2020
 **/

public class OnBoardingNavController extends DualNavController {

    public OnBoardingNavController(Context context, View view) {
        setNavController(view);
        setContext(context);
    }

    void actionOpenDialog(@StringRes int message) {
        OnBoardingFragmentDirections.ActionOnboardingActivityToAlertDialogFragment navDirections =
                OnBoardingFragmentDirections.actionOnboardingActivityToAlertDialogFragment(
                        R.string.alert_title_invalid_weight_input,
                        message
                );

        getNavController().navigate(navDirections);
    }

    void actionToApp() {
        Intent intent = new Intent(getContext(), AppActivity.class);
        startActivity(intent);
    }
}
