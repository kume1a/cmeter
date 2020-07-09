package com.kumela.cmeter.ui.screens.registration.register;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.ui.common.activity.BaseActivity;
import com.kumela.cmeter.ui.screens.registration.AuthController;

public class RegisterActivity extends BaseActivity implements RegisterMvc.Listener, AuthController.Listener {

    private RegisterMvc mViewMvc;
    private RegisterNavController mNavController;
    private AuthController mAuthController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getViewMvcFactory().newInstance(RegisterMvc.class, null);
        mNavController = getNavControllerFactory().newInstance(RegisterNavController.class, this);
        mAuthController = getPresentationComponent().getAuthController();

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mAuthController.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mAuthController.unregisterListener(this);
        mViewMvc.dismissProgressIndication();
    }

    @Override
    public void onRegisterClick(@NonNull String username, @NonNull String email, @NonNull String password) {
        boolean validUsername = validUsername(username);
        boolean validEmail = validEmail(email);
        boolean validPassword = validPassword(password);

        if (validUsername && validEmail && validPassword) {
            mViewMvc.showProgressIndication();
            mAuthController.registerAndNotify(email, password);
        }
    }

    private boolean validUsername(@NonNull String username) {
        if (username.length() < 4 || username.length() > 15) {
            mViewMvc.onUsernameError(getString(R.string.error_username_length));
            return false;
        } else if (!username.matches(Constants.VALID_ASCII_REGEX)) {
            mViewMvc.onUsernameError(getString(R.string.error_username_invalid));
            return false;
        }
        mViewMvc.removeUsernameError();
        return true;
    }

    private boolean validEmail(@NonNull String email) {
        if (!email.matches(Constants.EMAIL_REGEX)) {
            mViewMvc.onEmailError(getString(R.string.error_email_invalid));
            return false;
        }
        mViewMvc.removeEmailError();
        return true;
    }

    private boolean validPassword(@NonNull String password) {
        if (password.length() < 6) {
            mViewMvc.onPasswordError(getString(R.string.error_password_weak));
            return false;
        } else if (!password.matches(Constants.VALID_ASCII_REGEX)) {
            mViewMvc.onPasswordError(getString(R.string.error_password_invalid));
            return false;
        }
        mViewMvc.removePasswordError();
        return true;
    }

    @Override
    public void onSuccess() {
        mViewMvc.dismissProgressIndication();
        mNavController.actionToOnBoardingActivity();
    }

    @Override
    public void onFailure(String errorMsg) {
        mViewMvc.dismissProgressIndication();
        mViewMvc.onRegistrationFailure(errorMsg);
    }
}