package com.kumela.cmeter.ui.screens.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.base.BaseActivity;
import com.kumela.cmeter.ui.screens.starter.StarterActivity;

/**
 * Created by Toko on 10,July,2020
 **/
// git reset --hard <commidId> && git clean -f
public class AppActivity extends BaseActivity {

    public static final String EXTRA_UID = "EXTRA_UID";

    private AppBarConfiguration mAppBarConfiguration;

    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity);

        mAppBarLayout = findViewById(R.id.abl_main_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_nutrition, R.id.nav_water, R.id.nav_activity)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_app);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_nav_sign_out) {
                getPresentationComponent().getAuthController().signOut();
                startActivity(new Intent(this, StarterActivity.class));
                finish();
                return false;
            } else {
                NavigationUI.onNavDestinationSelected(item, navController);
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        AppViewModel appViewModel = new ViewModelProvider(this, getViewModelFactory()).get(AppViewModel.class);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            if (id != R.id.addFoodFragment && id != R.id.searchFragment && id != R.id.nutritionDetailsFragment) {
                appViewModel.setPlayAddFoodAnimation(true);
            }
            resetToolbar();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_app);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void resetToolbar() {
        mAppBarLayout.setExpanded(true, true);
    }
}
