package com.kumela.cmeter.common.di.presentation;

import com.kumela.cmeter.common.di.factory.NavControllerFactory;
import com.kumela.cmeter.common.di.factory.ViewModelFactory;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.ui.screens.registration.AuthController;

import dagger.Subcomponent;

/**
 * Created by Toko on 29,June,2020
 **/

@Subcomponent(modules = {PresentationModule.class, ViewModelModule.class})
public interface PresentationComponent {
    ViewMvcFactory getViewMvcFactory();
    NavControllerFactory getNavControllerFactory();
    ViewModelFactory getViewModelFactory();

    AuthController getAuthController();
}
