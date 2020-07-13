package com.kumela.cmeter.ui.screens.starter.authentication.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.ui.common.base.BaseFragment;
import com.kumela.cmeter.network.firebase.FirebaseAuthHandler;

public class RegisterFragment extends BaseFragment implements RegisterMvc.Listener, FirebaseAuthHandler.Listener {

    private RegisterMvc mViewMvc;
    private RegisterNavController mNavController;
    private FirebaseAuthHandler mFirebaseAuthHandler;

    private String mEmail, mUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(RegisterMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavController = getNavControllerFactory().newInstance(RegisterNavController.class, view);
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
        mViewMvc.dismissProgressIndication();
    }

    @Override
    public void onRegisterClick(@NonNull String username, @NonNull String email, @NonNull String password) {
        boolean validUsername = validUsername(username);
        boolean validEmail = validEmail(email);
        boolean validPassword = validPassword(password);

        if (validUsername && validEmail && validPassword) {
            mViewMvc.showProgressIndication();
            mFirebaseAuthHandler.registerAndNotify(email, password);

            mUsername = username;
            mEmail = email;
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
        mNavController.actionToOnBoardingActivity(mUsername, mEmail);
    }

    @Override
    public void onFailure(Integer errorMsgId) {
        mViewMvc.dismissProgressIndication();
        if (errorMsgId != null) {
            mViewMvc.showErrorSnackar(getResources().getString(errorMsgId));
        }
    }
}