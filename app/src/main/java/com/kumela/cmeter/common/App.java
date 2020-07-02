package com.kumela.cmeter.common;

import android.app.Application;

import com.kumela.cmeter.common.di.application.ApplicationComponent;
import com.kumela.cmeter.common.di.application.ApplicationModule;
import com.kumela.cmeter.common.di.application.DaggerApplicationComponent;

/**
 * Created by Toko on 23,June,2020
 **/

public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
