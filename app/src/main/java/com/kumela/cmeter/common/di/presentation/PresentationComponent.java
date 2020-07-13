package com.kumela.cmeter.common.di.presentation;

import com.google.firebase.auth.FirebaseUser;
import com.kumela.cmeter.common.di.factory.NavControllerFactory;
import com.kumela.cmeter.common.di.factory.ViewModelFactory;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.network.firebase.FirebaseAuthHandler;
import com.kumela.cmeter.network.firebase.FirebaseUserHandler;

import dagger.Subcomponent;

/**
 * Created by Toko on 29,June,2020
 **/

@Subcomponent(modules = {PresentationModule.class, ViewModelModule.class})
public interface PresentationComponent {
    ViewMvcFactory getViewMvcFactory();
    NavControllerFactory getNavControllerFactory();
    ViewModelFactory getViewModelFactory();

    FirebaseAuthHandler getAuthController();
    FirebaseUserHandler getFirebaseUserHandler();
}
