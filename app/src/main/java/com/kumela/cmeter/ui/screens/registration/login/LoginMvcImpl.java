package com.kumela.cmeter.ui.screens.registration.login;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvcImpl;

/**
 * Created by Toko on 07,July,2020
 **/

public class LoginMvcImpl extends BaseObservableViewMvcImpl<LoginMvc.Listener> implements LoginMvc {

    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;

    private Dialog mDialog;

    @SuppressWarnings("ConstantConditions")
    public LoginMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.login_activity, parent, false));

        tilEmail = findViewById(R.id.til_login_email);
        tilPassword = findViewById(R.id.til_login_password);

        MaterialButton btnLogin = findViewById(R.id.btn_login);
        MaterialButton btnForgotPassword = findViewById(R.id.btn_login_forgot_password);
        MaterialButton btnRegister = findViewById(R.id.btn_login_register);

        AppCompatImageButton btnFacebook = findViewById(R.id.btn_login_facebook);
        AppCompatImageButton btnGoogle = findViewById(R.id.btn_login_google);

        btnLogin.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onLoginClick(
                        tilEmail.getEditText().getText().toString(),
                        tilPassword.getEditText().getText().toString()
                );
        });
        btnForgotPassword.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onForgotPasswordClick();
        });
        btnRegister.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onRegisterClick();
        });
        btnFacebook.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onFacebookSignInClick();
        });
        btnGoogle.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onGoogleSignInClick();
        });


    }

    @Override
    public void onEmailError(String errorMsg) {
        tilEmail.setErrorEnabled(true);
        tilEmail.setError(errorMsg);
    }

    @Override
    public void onPasswordError(String errorMsg) {
        tilPassword.setErrorEnabled(true);
        tilPassword.setError(errorMsg);
    }

    @Override
    public void removeEmailError() {
        tilEmail.setErrorEnabled(false);
        tilEmail.setError(null);
    }

    @Override
    public void removePasswordError() {
        tilPassword.setErrorEnabled(false);
        tilPassword.setError(null);
    }

    @Override
    public void onLoginFailure(String errorMsg) {
        Snackbar snackbar = Snackbar.make(getRootView(), errorMsg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public void dismissProgressIndication() {
        if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void showProgressIndication() {
        if (mDialog == null) {
            mDialog = new Dialog(getContext());
            mDialog.setCancelable(false);
            mDialog.setContentView(new ProgressBar(getContext()));
            mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        mDialog.show();
    }
}
