package com.kumela.cmeter.ui.common.base;

import android.view.WindowManager;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.kumela.cmeter.R;
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

    private Toolbar mToolbar;

    @UiThread
    protected void disableToolbarScrollingBehavior() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) getToolbar().getLayoutParams();
        params.setScrollFlags(0);
    }

    @UiThread
    protected void enableToolbarScrollingBehavior() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) getToolbar().getLayoutParams();
        params.setScrollFlags(
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
        );
    }

    @UiThread
    protected Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = requireActivity().findViewById(R.id.toolbar);
        }
        return mToolbar;
    }

    @UiThread
    protected void disableWindowTouchEvents() {
        requireActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        );
    }

    protected void enableWindowTouchEvents() {
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

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
