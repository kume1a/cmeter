package com.kumela.cmeter.ui.screens.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.screens.nutrition.home.NutritionHomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, NutritionHomeActivity.class));
            finish();
        }, 300);
    }
}