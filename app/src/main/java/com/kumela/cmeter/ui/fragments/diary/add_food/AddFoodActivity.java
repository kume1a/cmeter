package com.kumela.cmeter.ui.fragments.diary.add_food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.BaseActivity;

public class AddFoodActivity extends BaseActivity {

    private static final int REQUEST_SEARCH = 0;

    private static final String TAG = "AddFoodActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        setupToolbar();

        findViewById(R.id.cv_search).setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(AddFoodActivity.this, SearchActivity.class),
                    REQUEST_SEARCH);
        });
    }

    private void setupToolbar() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            AddFoodActivityArgs args = AddFoodActivityArgs.fromBundle(bundle);
            String title = args.getTitle();

            Toolbar toolbar = findViewById(R.id.toolbar_add_food);
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        } else {
            throw new IllegalArgumentException("Starting " + getClass().getSimpleName() + " requires argument: Title ");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == REQUEST_SEARCH) {
                String query = data.getStringExtra(SearchActivity.EXTRA_SEARCH_QUERY);
                Log.d(TAG, "onActivityResult: query = " + query);
            }
        }
    }
}