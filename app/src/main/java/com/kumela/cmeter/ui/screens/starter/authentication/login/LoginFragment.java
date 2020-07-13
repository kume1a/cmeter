package com.kumela.cmeter.ui.screens.starter.authentication.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.network.firebase.FirebaseAuthHandler;
import com.kumela.cmeter.ui.common.base.BaseFragment;

public class LoginFragment extends BaseFragment implements LoginMvc.Listener, FirebaseAuthHandler.Listener {

    private LoginMvc mViewMvc;
    private LoginNavController mNavController;

    private FirebaseAuthHandler mFirebaseAuthHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(LoginMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = getNavControllerFactory().newInstance(LoginNavController.class, getContext(), view);
        mFirebaseAuthHandler = getPresentationComponent().getAuthController();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFirebaseAuthHandler.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFirebaseAuthHandler.unregisterListener(this);
    }

    @Override
    public void onLoginClick(@NonNull String email, @NonNull String password) {
        boolean validEmail = validEmail(email);
        boolean validPassword = validPassword(password);

        if (validEmail && validPassword) {
            mViewMvc.showProgressIndication();
            mFirebaseAuthHandler.loginAndNotify(email, password);
        }
    }

    @Override
    public void onForgotPasswordClick() {
        Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGoogleSignInClick() {
        Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFacebookSignInClick() {
        Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterClick() {
        mNavController.actionToRegister();
    }

    @Override
    public void onSuccess() {
        mViewMvc.dismissProgressIndication();
        mNavController.actionToApp(mFirebaseAuthHandler.getUid());
    }

    @Override
    public void onFailure(Integer errorMsgId) {
        mViewMvc.dismissProgressIndication();
        if (errorMsgId != null) {
            mViewMvc.onLoginFailure(getResources().getString(errorMsgId));
        }
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