package com.kumela.cmeter.ui.common.nav;


import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * Created by Toko on 10,July,2020
 **/

public abstract class BaseNavController {

    private NavController mNavController;

    protected void setNavController(View view) {
        this.mNavController = Navigation.findNavController(view);
    }

    protected NavController getNavController() {
        if (mNavController == null) {
            throw new RuntimeException("Nav controller must be initialized before calling getter method");
        }
        return mNavController;
    }
}
