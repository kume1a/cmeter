package com.kumela.cmeter.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.BaseActivity;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;


//    https://docs.google.com/document/d/1_q-K-ObMTZvO0qUEAxROrN3bwMujwAN25sLHwJzliK0/edit#
//    https://developer.nutritionix.com/

//    @atheros raywenderlich
//    .Rg5&muXT6$@x93

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorText));
        setSupportActionBar(toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_diary,
                R.id.nav_activity,
                R.id.nav_profile
        ).build();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow);
        });

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<NutritionResponse> responseCall = retrofit.create(NutritionXService.class)
                .getNutritionInfo(
                        new RequestData(
                                "eggs",
                                false,
                                false,
                                "Asia/Tbilisi",
                                false
                        ));

        Log.d(TAG, "onCreate: enqueue started");
        responseCall.enqueue(new Callback<NutritionResponse>() {
            @Override
            public void onResponse(@NonNull Call<NutritionResponse> call, @NonNull Response<NutritionResponse> response) {
                if (response.isSuccessful()) {
                    NutritionResponse nutritionResponse = response.body();
                    if (nutritionResponse != null) {
                        Log.d(TAG, "onResponse: nutritionResponse = " + nutritionResponse.toString());
                    } else {
                        Log.e(TAG, "onResponse: nutrition response = null", new NullPointerException());
                        onFailure(call, new Exception());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NutritionResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                // TODO: 6/21/2020 show error screen
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}