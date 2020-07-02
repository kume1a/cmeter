package com.kumela.cmeter.ui.screens.water;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.activity.BaseDrawerActivity;

/**
 * Created by Toko on 30,June,2020
 **/

public class WaterHomeActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_home_activity);

        setupToolbar(R.id.toolbar_water, R.id.drawer_water, R.id.nav_water, getString(R.string.title_water));
    }
}
