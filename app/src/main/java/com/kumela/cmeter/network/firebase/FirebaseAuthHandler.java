package com.kumela.cmeter.network.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.BaseObservable;

import javax.inject.Inject;

/**
 * Created by Toko on 07,July,2020
 **/

public class FirebaseAuthHandler extends BaseObservable<FirebaseAuthHandler.Listener> {

    public interface Listener {
        void onSuccess();

        void onFailure(Integer errorMsgId);
    }

    private FirebaseAuth mAuth;

    @Inject
    public FirebaseAuthHandler(FirebaseAuth firebaseAuth) {
        this.mAuth = firebaseAuth;
    }

    @Nullable
    public String getUid() {
        return mAuth.getUid();
    }

    public void signOut() {
        mAuth.signOut();
    }

    public void loginAndNotify(@NonNull String email, @NonNull String password) {
        Task<AuthResult> authResult = mAuth.signInWithEmailAndPassword(email, password);
        addListener(authResult);
    }

    public void registerAndNotify(@NonNull String email, @NonNull String password) {
        Task<AuthResult> authResult = mAuth.createUserWithEmailAndPassword(email, password);
        addListener(authResult);
    }

    private void addListener(Task<AuthResult> task) {
        task.addOnSuccessListener(authResult -> {
            FirebaseUser user = authResult.getUser();
            if (user != null) {
                for (Listener listener : getListeners()) {
                    listener.onSuccess();
                }
            } else {
                for (Listener listener : getListeners()) {
                    listener.onFailure(null);
                }
            }
        }).addOnFailureListener(e -> {
            for (Listener listener : getListeners()) {
                if (e instanceof FirebaseAuthException) {
                    listener.onFailure(exceptionMessage((FirebaseAuthException) e));
                } else if (e instanceof FirebaseNetworkException) {
                    listener.onFailure(R.string.firebase_exception_network);
                }
            }
        });
    }

    @NonNull
    private Integer exceptionMessage(@Nullable FirebaseAuthException e) {
        if (e == null) {
            return R.string.error_authentication_failed;
        }

        int s;
        switch (e.getErrorCode()) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                s = R.string.error_invalid_custom_token;
                break;
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                s = R.string.error_custom_token_mismatch;
                break;
            case "ERROR_INVALID_CREDENTIAL":
                s = R.string.error_invalid_credential;
                break;
            case "ERROR_INVALID_EMAIL":
                s = R.string.error_invalid_email;
                break;
            case "ERROR_WRONG_PASSWORD":
                s = R.string.error_wrong_password;
                break;
            case "ERROR_USER_MISMATCH":
                s = R.string.error_user_missmatch;
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                s = R.string.error_requires_recent_login;
                break;
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                s = R.string.error_account_exists_with_different_credential;
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                s = R.string.error_email_already_in_use;
                break;
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                s = R.string.error_credential_already_in_use;
                break;
            case "ERROR_USER_DISABLED":
                s = R.string.error_user_disabled;
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
            case "ERROR_INVALID_USER_TOKEN":
                s = R.string.error_user_token_expired;
                break;
            case "ERROR_USER_NOT_FOUND":
                s = R.string.error_user_not_found;
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                s = R.string.error_operation_not_allowed;
                break;
            case "ERROR_WEAK_PASSWORD":
                s = R.string.error_weak_pssword;
                break;
            default:
                s = R.string.error_authentication_failed;
                break;
        }
        return s;
    }
}
