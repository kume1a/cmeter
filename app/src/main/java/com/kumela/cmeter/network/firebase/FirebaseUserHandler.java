package com.kumela.cmeter.network.firebase;

import androidx.annotation.StringRes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.firebase.User;

import javax.inject.Inject;

/**
 * Created by Toko on 13,July,2020
 **/

public class FirebaseUserHandler extends BaseObservable<FirebaseUserHandler.Listener> {

    public interface Listener {
        void onUserCreated();

        void onUserCreateFailed(@StringRes int errorMsgId);
    }

    private DatabaseReference mDatabase;
    private String uid;

    @Inject
    public FirebaseUserHandler(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth) {
        this.mDatabase = firebaseDatabase.getReference();
        this.uid = firebaseAuth.getUid();
    }

    public String getUid() {
        return uid;
    }

    public void createUserAndNotify(User user) {
        mDatabase.child(Constants.CHILD_USERS)
                .child(user.uid)
                .setValue(user)
                .addOnSuccessListener(aVoid -> {
                    for (Listener listener : getListeners()) listener.onUserCreated();
                })
                .addOnFailureListener(e -> {
                    for (Listener listener : getListeners()) listener.onUserCreateFailed(R.string.error_something_went_wrong);
                });
    }
}
