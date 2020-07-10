package com.kumela.cmeter.ui.screens.registration.login;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.ui.common.base.BaseActivity;
import com.kumela.cmeter.ui.screens.registration.AuthController;

public class LoginActivity extends BaseActivity implements LoginMvc.Listener, AuthController.Listener {

    private LoginMvc mViewMvc;
    private LoginNavController mNavController;

    private AuthController mAuthController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getViewMvcFactory().newInstance(LoginMvc.class, null);
        mNavController = getNavControllerFactory().newInstance(LoginNavController.class, this);

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
    }

    @Override
    public void onLoginClick(@NonNull String email, @NonNull String password) {
        boolean validEmail = validEmail(email);
        boolean validPassword = validPassword(password);

        if (validEmail && validPassword) {
            mViewMvc.showProgressIndication();
            mAuthController.loginAndNotify(email, password);
        }
    }

    @Override
    public void onForgotPasswordClick() {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGoogleSignInClick() {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFacebookSignInClick() {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterClick() {
        mNavController.actionToRegister();
    }

    @Override
    public void onSuccess() {
        mViewMvc.dismissProgressIndication();
        mNavController.actionToOnBoarding();
        finish(); // TODO: 7/8/2020 intent flags not working
    }

    @Override
    public void onFailure(String errorMsg) {
        mViewMvc.dismissProgressIndication();
        mViewMvc.onLoginFailure(errorMsg);
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
}