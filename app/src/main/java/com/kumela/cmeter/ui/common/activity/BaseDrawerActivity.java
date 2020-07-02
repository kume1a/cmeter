package com.kumela.cmeter.ui.common.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.activity.BaseActivity;
import com.kumela.cmeter.ui.screens.activity.ActivityHomeActivity;
import com.kumela.cmeter.ui.screens.nutrition.home.NutritionHomeActivity;
import com.kumela.cmeter.ui.screens.water.WaterHomeActivity;

/**
 * Created by Toko on 29,June,2020
 **/

public abstract class BaseDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_left);
    }

    protected void setupToolbar(int toolbarId, int drawerId, int navId, String title) {
        mDrawerLayout = findViewById(drawerId);
        Toolbar toolbar = findViewById(toolbarId);
        NavigationView navView = findViewById(navId);

        int selectedItem = -1;
        switch (navId) {
            case R.id.nav_nutrition:
                selectedItem = R.id.menu_nav_nutrition;
                break;
            case R.id.nav_water:
                selectedItem = R.id.menu_nav_water;
                break;
            case R.id.nav_activity:
                selectedItem = R.id.menu_nav_activity;
                break;
        }
        navView.setCheckedItem(selectedItem);

        navView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_navigation);
        toolbar.setNavigationOnClickListener(v -> {
            int gravity = GravityCompat.START;
            if (mDrawerLayout.isDrawerOpen(gravity)) {
                mDrawerLayout.closeDrawer(gravity);
            } else {
                mDrawerLayout.openDrawer(gravity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else super.onBackPressed();
    }

    @Nullable
    private String getActivityName() {
        PackageManager packageManager = getPackageManager();
        try {
            ActivityInfo info = packageManager.getActivityInfo(getComponentName(), 0);
            return info.name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_nav_nutrition:
                if (!NutritionHomeActivity.class.getName().equals(getActivityName())) {
                    intent = new Intent(this, NutritionHomeActivity.class);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.menu_nav_activity:
                if (!ActivityHomeActivity.class.getName().equals(getActivityName())) {
                    intent = new Intent(this, ActivityHomeActivity.class);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.menu_nav_water:
                if (!WaterHomeActivity.class.getName().equals(getActivityName())) {
                    intent = new Intent(this, WaterHomeActivity.class);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
        }

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return true;
    }
}
