package com.kumela.cmeter.ui.screens.app;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.base.BaseActivity;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;
import com.kumela.cmeter.ui.screens.starter.StarterActivity;

/**
 * Created by Toko on 10,July,2020
 **/
// git reset --hard <commidId> && git clean -f
public class AppActivity extends BaseActivity implements ToolbarHelper {

    public static final String EXTRA_UID = "EXTRA_UID";

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity);

        mAppBarLayout = findViewById(R.id.abl_main_nav);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

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

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.addFoodFragment) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mAppBarLayout.setExpanded(true, true);
            } else {
                resetToolbar();
            }
        });
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
    public <T extends View> T findMenuView(int id) {
        return mToolbar.findViewById(id);
    }

    private void resetToolbar() {
        if (mToolbar.getBackground() != null && ((ColorDrawable) mToolbar.getBackground()).getColor() !=
                getResources().getColor(R.color.colorPrimaryDark)) {
            mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        mAppBarLayout.setExpanded(true, true);
    }
}
