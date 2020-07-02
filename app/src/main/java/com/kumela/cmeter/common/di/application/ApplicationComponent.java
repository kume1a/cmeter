package com.kumela.cmeter.common.di.application;

import com.kumela.cmeter.common.di.presentation.PresentationComponent;
import com.kumela.cmeter.common.di.presentation.PresentationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Toko on 29,June,2020
 **/

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
