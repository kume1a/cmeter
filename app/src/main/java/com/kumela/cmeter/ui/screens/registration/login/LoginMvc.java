package com.kumela.cmeter.ui.screens.registration.login;

import androidx.annotation.NonNull;

import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 07,July,2020
 **/

public interface LoginMvc extends BaseObservableViewMvc<LoginMvc.Listener> {

    interface Listener {
        void onLoginClick(@NonNull String email, @NonNull String password);

        void onRegisterClick();

        void onForgotPasswordClick();

        void onGoogleSignInClick();

        void onFacebookSignInClick();
    }

    void onEmailError(String errorMsg);

    void onPasswordError(String errorMsg);

    void removeEmailError();

    void removePasswordError();

    void onLoginFailure(String errorMsg);

    void dismissProgressIndication();

    void showProgressIndication();
}
