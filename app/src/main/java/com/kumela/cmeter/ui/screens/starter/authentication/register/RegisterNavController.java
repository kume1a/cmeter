package com.kumela.cmeter.ui.screens.starter.authentication.register;

import android.view.View;

import com.kumela.cmeter.ui.common.nav.FragmentNavController;

/**
 * Created by Toko on 08,July,2020
 **/

public class RegisterNavController extends FragmentNavController {

    public RegisterNavController(View rootView) {
        setNavController(rootView);
    }

    void actionToOnBoardingActivity(String username, String email) {
        RegisterFragmentDirections.ActionRegisterFragmentToOnboardingActivity navDirections =
                RegisterFragmentDirections.actionRegisterFragmentToOnboardingActivity(
                        username,
                        email
                );
        getNavController().navigate(navDirections);
    }
}
