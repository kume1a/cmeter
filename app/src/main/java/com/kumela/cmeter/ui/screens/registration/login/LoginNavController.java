package com.kumela.cmeter.ui.screens.registration.login;

import android.content.Context;
import android.content.Intent;

import com.kumela.cmeter.ui.common.ContextWrapper;
import com.kumela.cmeter.ui.screens.onboarding.OnboardingActivity;
import com.kumela.cmeter.ui.screens.registration.register.RegisterActivity;

/**
 * Created by Toko on 07,July,2020
 **/

public class LoginNavController implements ContextWrapper {

    private Context mContext;

    public LoginNavController(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    void actionToRegister() {
        final Intent intent = new Intent(getContext(), RegisterActivity.class);
        getContext().startActivity(intent);
    }

    void actionToOnBoarding() {
        final Intent intent = new Intent(getContext(), OnboardingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getContext().startActivity(intent);
    }
}
