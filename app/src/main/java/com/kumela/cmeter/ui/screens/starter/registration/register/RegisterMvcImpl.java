package com.kumela.cmeter.ui.screens.starter.registration.register;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvcImpl;

/**
 * Created by Toko on 07,July,2020
 **/

public class RegisterMvcImpl extends BaseObservableViewMvcImpl<RegisterMvc.Listener>
        implements RegisterMvc {

    private TextInputLayout tilUsername;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;

    private Dialog mDialog;

    @SuppressWarnings("ConstantConditions")
    public RegisterMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.register_fragment, parent, false));

        tilUsername = findViewById(R.id.til_register_username);
        tilEmail = findViewById(R.id.til_register_email);
        tilPassword = findViewById(R.id.til_register_password);

        MaterialButton btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onRegisterClick(
                        tilUsername.getEditText().getText().toString(),
                        tilEmail.getEditText().getText().toString(),
                        tilPassword.getEditText().getText().toString()
                );
            }
        });
    }

    @Override
    public void onUsernameError(String errorMsg) {
        tilUsername.setErrorEnabled(true);
        tilUsername.setError(errorMsg);
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
    public void removeUsernameError() {
        tilUsername.setErrorEnabled(false);
        tilUsername.setError(null);
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
    public void onRegistrationFailure(String errorMsg) {
        Snackbar snackbar = Snackbar.make(getRootView(), errorMsg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public void dismissProgressIndication() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
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
