package com.kumela.cmeter.common.di.application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
}
