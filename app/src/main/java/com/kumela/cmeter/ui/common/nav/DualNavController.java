package com.kumela.cmeter.ui.common.nav;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * Created by Toko on 13,July,2020
 **/

public abstract class DualNavController {

    private NavController mNavController;
    private Context mContext;

    protected void setNavController(View view) {
        this.mNavController = Navigation.findNavController(view);
    }

    protected void setContext(Context context) {
        this.mContext = context;
    }

    protected NavController getNavController() {
        if (mNavController == null) {
            throw new RuntimeException("Nav controller must be initialized before calling getter method");
        }
        return mNavController;
    }

    protected Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("setContext() must be called before calling getter method");
        }
        return mContext;
    }

    protected void startActivity(Intent intent) {
        if (mContext == null) {
            throw new RuntimeException("setContext() must be called before calling startActivity()");
        }
        mContext.startActivity(intent);
    }
}
