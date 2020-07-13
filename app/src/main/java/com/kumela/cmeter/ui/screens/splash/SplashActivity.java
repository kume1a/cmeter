package com.kumela.cmeter.ui.screens.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.network.firebase.FirebaseAuthHandler;
import com.kumela.cmeter.ui.common.base.BaseActivity;
import com.kumela.cmeter.ui.screens.app.AppActivity;
import com.kumela.cmeter.ui.screens.starter.StarterActivity;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        FirebaseAuthHandler firebaseAuthHandler = getPresentationComponent().getAuthController();
        String uid = firebaseAuthHandler.getUid();
        Log.d(TAG, "onCreate: uid = " + uid);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent;
            if (uid != null) {
                Log.d(TAG, "onCreate: navigating to AppActivity");
                intent = new Intent(this, AppActivity.class);
            } else {
                Log.d(TAG, "onCreate: navigating to StarterActivity");
                intent = new Intent(this, StarterActivity.class);
            }

            startActivity(intent);
            finish();
        }, 500);

    }
}