package com.kumela.cmeter.ui.common.base;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.kumela.cmeter.common.App;
import com.kumela.cmeter.common.di.application.ApplicationComponent;
import com.kumela.cmeter.common.di.factory.NavControllerFactory;
import com.kumela.cmeter.common.di.factory.ViewModelFactory;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.common.di.presentation.PresentationComponent;
import com.kumela.cmeter.common.di.presentation.PresentationModule;

/**
 * Created by Toko on 22,June,2020
 **/

public abstract class BaseActivity extends AppCompatActivity {

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(this));
    }

    @UiThread
    protected ViewModelFactory getViewModelFactory() {
        return getPresentationComponent().getViewModelFactory();
    }

    @UiThread
    protected NavControllerFactory getNavControllerFactory() {
        return getPresentationComponent().getNavControllerFactory();
    }

    @UiThread
    protected ViewMvcFactory getViewMvcFactory() {
        return getPresentationComponent().getViewMvcFactory();
    }

    private ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

}
