package com.kumela.cmeter.ui.screens.starter.registration.login;

import android.view.View;

import androidx.navigation.NavDirections;

import com.kumela.cmeter.ui.common.nav.BaseNavController;

/**
 * Created by Toko on 07,July,2020
 **/

public class LoginNavController extends BaseNavController {

    public LoginNavController(View view) {
        setNavController(view);
    }

    void actionToRegister() {
        NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
        getNavController().navigate(navDirections);
    }

    void actionToOnBoarding() {
        NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToOnboardingActivity();
        getNavController().navigate(navDirections);
    }
}
