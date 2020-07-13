package com.kumela.cmeter.ui.common.base;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.kumela.cmeter.common.App;
import com.kumela.cmeter.common.di.application.ApplicationComponent;
import com.kumela.cmeter.common.di.factory.NavControllerFactory;
import com.kumela.cmeter.common.di.factory.ViewModelFactory;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.common.di.presentation.PresentationComponent;
import com.kumela.cmeter.common.di.presentation.PresentationModule;

/**
 * Created by Toko on 09,July,2020
 **/

public abstract class BaseFragment extends Fragment {
    @UiThread
    protected PresentationComponent getPresentationComponent() {
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule((AppCompatActivity) requireActivity()));
    }

    @UiThread
    protected NavControllerFactory getNavControllerFactory() {
        return getPresentationComponent().getNavControllerFactory();
    }

    @UiThread
    protected ViewMvcFactory getViewMvcFactory() {
        return getPresentationComponent().getViewMvcFactory();
    }

    @UiThread
    protected ViewModelFactory getViewModelFactory() {
        return getPresentationComponent().getViewModelFactory();
    }

    private ApplicationComponent getApplicationComponent() {
        return ((App) requireActivity().getApplication()).getApplicationComponent();
    }
}
