package com.kumela.cmeter.ui.screens.starter.authentication.register;

import androidx.annotation.NonNull;

import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 07,July,2020
 **/

public interface RegisterMvc extends ObservableViewMvc<RegisterMvc.Listener> {

    void onUsernameError(String errorMsg);

    void onEmailError(String errorMsg);

    void onPasswordError(String errorMsg);

    void removeUsernameError();

    void removeEmailError();

    void removePasswordError();

    void showErrorSnackar(String errorMsg);

    void dismissProgressIndication();

    void showProgressIndication();

    interface Listener {
        void onRegisterClick(@NonNull String username, @NonNull String email, @NonNull String password);
    }
}
