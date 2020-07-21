package com.kumela.cmeter.common.di.application;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.firebase.User;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Toko on 07,July,2020
 **/

@Module
public abstract class FirebaseModule {

    @Singleton
    @Provides
    static FirebaseDatabase providesFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    static FirebaseAuth providesFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    static String providesUid(FirebaseAuth firebaseAuth) {
        return firebaseAuth.getUid();
    }
}
