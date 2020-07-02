package com.kumela.cmeter.ui.screens.nutrition.food_details;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kumela.cmeter.R;

/**
 * Created by Toko on 30,June,2020
 **/

public class FoodDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_details_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_food_details);
        toolbar.setTitle("Banana");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
    }
}
