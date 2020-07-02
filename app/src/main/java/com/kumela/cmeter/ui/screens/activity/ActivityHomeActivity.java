package com.kumela.cmeter.ui.screens.activity;

import android.os.Bundle;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.activity.BaseDrawerActivity;

/**
 * Created by Toko on 30,June,2020
 **/

public class ActivityHomeActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        setupToolbar(R.id.toolbar_activity, R.id.drawer_activity, R.id.nav_activity, getString(R.string.title_activity));
    }
}