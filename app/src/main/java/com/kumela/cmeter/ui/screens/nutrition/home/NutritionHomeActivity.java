package com.kumela.cmeter.ui.screens.nutrition.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.activity.BaseDrawerActivity;

public class NutritionHomeActivity extends BaseDrawerActivity implements NutritionHomeMvcImpl.Listener {

    private NutritionHomeMvcImpl mController;
    private NutritionHomeNavController mNavController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = getViewMvcFactory().newInstance(NutritionHomeMvcImpl.class, null);
        mNavController = getPresentationComponent().getHomeNavController();
        setContentView(mController.getRootView());
        setupToolbar(R.id.toolbar_nutrition, R.id.drawer_nutrition, R.id.nav_nutrition, getString(R.string.title_nutrition));
    }

    @Override
    public void onStart() {
        super.onStart();
        mController.init();
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
    public void onNavigateToAddFood(boolean isMenuOpen, @Nullable String title) {
        if (isMenuOpen) {
            mController.closeMenu();
        } else mController.openMenu();

        if (title != null) mNavController.toAddFood(title);
    }
}