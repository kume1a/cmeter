package com.kumela.cmeter.ui.screens.starter.registration.register;

import androidx.annotation.NonNull;

import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 07,July,2020
 **/

public interface RegisterMvc extends BaseObservableViewMvc<RegisterMvc.Listener> {

    void onUsernameError(String errorMsg);

    void onEmailError(String errorMsg);

    void onPasswordError(String errorMsg);

    void removeUsernameError();

    void removeEmailError();

    void removePasswordError();

    void onRegistrationFailure(String errorMsg);

    void dismissProgressIndication();

    void showProgressIndication();

    interface Listener {
        void onRegisterClick(@NonNull String username, @NonNull String email, @NonNull String password);
    }
}
