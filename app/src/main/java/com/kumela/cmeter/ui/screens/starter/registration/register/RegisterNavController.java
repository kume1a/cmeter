package com.kumela.cmeter.ui.screens.starter.registration.register;

import android.view.View;

import androidx.navigation.NavDirections;

import com.kumela.cmeter.ui.common.nav.BaseNavController;

/**
 * Created by Toko on 08,July,2020
 **/

public class RegisterNavController extends BaseNavController {

    public RegisterNavController(View rootView) {
        setNavController(rootView);
    }

    void actionToOnBoardingActivity() {
        NavDirections navDirections = RegisterFragmentDirections.actionRegisterFragmentToOnboardingActivity();
        getNavController().navigate(navDirections);
    }
}
