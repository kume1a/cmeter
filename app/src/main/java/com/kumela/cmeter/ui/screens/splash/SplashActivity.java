package com.kumela.cmeter.ui.screens.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.base.BaseActivity;
import com.kumela.cmeter.ui.screens.nutrition.home.NutritionHomeActivity;
import com.kumela.cmeter.ui.screens.registration.AuthController;
import com.kumela.cmeter.ui.screens.registration.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        AuthController authController = getPresentationComponent().getAuthController();
        String uid = authController.getUid();
        Log.d(TAG, "onCreate: uid = " + uid);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent;
            if (uid != null) {
                Log.d(TAG, "onCreate: navigating to homeActivity");
                intent = new Intent(SplashActivity.this, NutritionHomeActivity.class);
            } else {
                Log.d(TAG, "onCreate: navigating to loginActivity");
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();
        }, 500);
    }
}