package com.kumela.cmeter.ui.screens.nutrition.add_food;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.screens.nutrition.add_food.tabs.TabsAdapter;
import com.kumela.cmeter.ui.common.activity.BaseActivity;

public class AddFoodActivity extends BaseActivity {

    public static final String EXTRA_ADD_FOOD_TITLE = "EXTRA_ADD_FOOD_TITLE";

    private AddFoodMvc mController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = getViewMvcFactory().newInstance(AddFoodMvc.class, null);
        setContentView(mController.getRootView());

        setupViewPager();
        setupToolbar(R.id.toolbar_add_food, EXTRA_ADD_FOOD_TITLE);
    }

    private void setupViewPager() {
        ViewPager2 viewPager = findViewById(R.id.vp_add_food);
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), getLifecycle()));

        TabLayout tabLayout = findViewById(R.id.tabs_add_food);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_recent);
                    tab.setText("Recent");
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_favorite);
                    tab.setText("Favorite");
                    break;
            }
        }).attach();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = mController.optionItemSelected(item);

        if (!result) return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_food, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mController.init();
    }
}