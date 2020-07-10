package com.kumela.cmeter.ui.common.base;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.App;
import com.kumela.cmeter.common.di.factory.NavControllerFactory;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.common.di.application.ApplicationComponent;
import com.kumela.cmeter.common.di.presentation.PresentationComponent;
import com.kumela.cmeter.common.di.presentation.PresentationModule;

/**
 * Created by Toko on 22,June,2020
 **/

public abstract class BaseActivity extends AppCompatActivity {

    protected void setupToolbar(int toolbarId, String title) {
        Toolbar toolbar = findViewById(toolbarId);

        if (title.startsWith("EXTRA")) {
            title = getIntent().getStringExtra(title);
        }
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    protected void setupToolbar(int toolbarId, int resId) {
        setupToolbar(toolbarId, getString(resId));
    }

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(this));
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
