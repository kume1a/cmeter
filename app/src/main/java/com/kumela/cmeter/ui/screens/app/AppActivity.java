package com.kumela.cmeter.ui.screens.app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;

/**
 * Created by Toko on 10,July,2020
 **/

public class AppActivity extends AppCompatActivity implements ToolbarHelper {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_nutrition, R.id.nav_water, R.id.nav_activity)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_app);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_app);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void setBackground(int colorId) {
        mToolbar.setBackgroundColor(getResources().getColor(colorId));
    }

    @Override
    public <T extends View> T findMenuView(int id) {
        return mToolbar.findViewById(id);
    }
}
