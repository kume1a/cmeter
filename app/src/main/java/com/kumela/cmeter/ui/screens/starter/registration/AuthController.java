package com.kumela.cmeter.ui.screens.starter.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvcImpl;

import javax.inject.Inject;

/**
 * Created by Toko on 07,July,2020
 **/

public class AuthController extends BaseObservableViewMvcImpl<AuthController.Listener> {

    public interface Listener {
        void onSuccess();

        void onFailure(String errorMsg);
    }

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    @Inject
    public AuthController(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth) {
        this.mDatabase = firebaseDatabase;
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
                listener.onFailure(exceptionMessage((FirebaseAuthException) e));
            }
        });
    }

    @NonNull
    private String exceptionMessage(@Nullable FirebaseAuthException e) {
        if (e == null) {
            return "Authentication failed";
        }

        String s;
        switch (e.getErrorCode()) {
            case "ERROR_INVALID_CUSTOM_TOKEN":
                s = "The custom token format is incorrect. Please check the documentation.";
                break;
            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                s = "The custom token corresponds to a different audience.";
                break;
            case "ERROR_INVALID_CREDENTIAL":
                s = "The supplied auth credential is malformed or has expired.";
                break;
            case "ERROR_INVALID_EMAIL":
                s = "The email address is badly formatted.";
                break;
            case "ERROR_WRONG_PASSWORD":
                s = "The password is invalid or the user does not have a password.";
                break;
            case "ERROR_USER_MISMATCH":
                s = "The supplied credentials do not correspond to the previously signed in user.";
                break;
            case "ERROR_REQUIRES_RECENT_LOGIN":
                s = "This operation is sensitive and requires recent authentication. Log in again before retrying this request.";
                break;
            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                s = "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.";
                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                s = "The email address is already in use by another account.";
                break;
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                s = "This credential is already associated with a different user account.";
                break;
            case "ERROR_USER_DISABLED":
                s = "The user account has been disabled by an administrator.";
                break;
            case "ERROR_USER_TOKEN_EXPIRED":
            case "ERROR_INVALID_USER_TOKEN":
                s = "The user's credential is no longer valid. The user must sign in again.";
                break;
            case "ERROR_USER_NOT_FOUND":
                s = "There is no user record corresponding to this identifier. The user may have been deleted.";
                break;
            case "ERROR_OPERATION_NOT_ALLOWED":
                s = "This operation is not allowed. You must enable this service in the console.";
                break;
            case "ERROR_WEAK_PASSWORD":
                s = "The given password is invalid.";
                break;
            default:
                s = "Authentication failed";
                break;
        }
        return s;
    }
}
