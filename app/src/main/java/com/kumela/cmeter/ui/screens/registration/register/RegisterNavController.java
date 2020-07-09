package com.kumela.cmeter.ui.screens.registration.register;

import android.content.Context;
import android.content.Intent;

import com.kumela.cmeter.ui.common.ContextWrapper;
import com.kumela.cmeter.ui.screens.onboarding.OnboardingActivity;

/**
 * Created by Toko on 08,July,2020
 **/

public class RegisterNavController implements ContextWrapper {

    private Context mContext;

    public RegisterNavController(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    void actionToOnBoardingActivity() {
        final Intent intent = new Intent(getContext(), OnboardingActivity.class);
        getContext().startActivity(intent);
    }
}
