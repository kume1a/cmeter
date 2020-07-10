package com.kumela.cmeter.ui.screens.nutrition.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.base.BaseDrawerActivity;

public class NutritionHomeActivity extends BaseDrawerActivity implements NutritionHomeMvc.Listener {

    private NutritionHomeMvc mController;
    private NutritionHomeNavController mNavController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = getViewMvcFactory().newInstance(NutritionHomeMvc.class, null);
        mNavController = getNavControllerFactory().newInstance(NutritionHomeNavController.class, this);
        setContentView(mController.getRootView());
        setupToolbar(R.id.toolbar_nutrition, R.id.drawer_nutrition, R.id.nav_nutrition, getString(R.string.title_nutrition));
    }

    @Override
    public void onStart() {
        super.onStart();
        mController.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mController.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nutrition, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_nutrition_stats) {
            Toast.makeText(this, "Statistics", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuClick(@NonNull String title) {
        mController.closeMenu();
        mNavController.actionToAddFood(title);
    }

    @Override
    public void onFabClick(boolean isMenuOpen) {
        if (isMenuOpen) {
            mController.closeMenu();
        } else mController.openMenu();
    }
}