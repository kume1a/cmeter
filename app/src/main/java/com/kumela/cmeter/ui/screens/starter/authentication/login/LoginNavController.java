package com.kumela.cmeter.ui.screens.starter.authentication.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.navigation.NavDirections;

import com.kumela.cmeter.ui.common.nav.DualNavController;
import com.kumela.cmeter.ui.screens.app.AppActivity;

/**
 * Created by Toko on 07,July,2020
 **/

public class LoginNavController extends DualNavController {

    public LoginNavController(Context context, View view) {
        setNavController(view);
        setContext(context);
    }

    void actionToRegister() {
        NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
        getNavController().navigate(navDirections);
    }

    public void actionToApp(String uid) {
        Intent intent = new Intent(getContext(), AppActivity.class);
        intent.putExtra(AppActivity.EXTRA_UID, uid);
        startActivity(intent);
    }
}
