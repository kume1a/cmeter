package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.R;
import com.kumela.cmeter.model.list.NutritionDetailItem;
import com.kumela.cmeter.ui.common.base.BaseActivity;

import java.util.List;

/**
 * Created by Toko on 30,June,2020
 **/

public class NutritionDetailsActivity extends BaseActivity
        implements NutritionDetailsViewModel.Listener {

    public static final String EXTRA_FOOD_NAME = "EXTRA_FOOD_NAME";

    private NutritionDetailsMvc mViewMvc;
    private NutritionDetailsViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getPresentationComponent().getViewMvcFactory().newInstance(NutritionDetailsMvc.class, null);

        mViewModel = new ViewModelProvider(
                this,
                getPresentationComponent().getViewModelFactory()
        ).get(NutritionDetailsViewModel.class);

        mViewModel.getBaseNutritionLiveData().observe(this, baseNutrition -> mViewMvc.bindBaseNutritionInfo(baseNutrition));
        mViewModel.getPhotoLicaData().observe(this, photo -> mViewMvc.bindImage(photo));

        setContentView(mViewMvc.getRootView());
        setupToolbar(R.id.toolbar_food_details, getFoodName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.registerListener(this);
        mViewModel.fetchNutritionInfoAndNotify(getFoodName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.unregisterListener(this);
    }

    private String getFoodName() {
        Intent intent = getIntent();
        if (!intent.hasExtra(EXTRA_FOOD_NAME)) {
            throw new RuntimeException("Starting " + getClass().getSimpleName() + " requires argument " + EXTRA_FOOD_NAME);
        }
        return intent.getStringExtra(EXTRA_FOOD_NAME);
    }

    @Override
    public void onProvideNutritionInfo(List<NutritionDetailItem> nutritionDetails) {
        mViewMvc.bindRecyclerNutritionInfo(nutritionDetails);
    }

    @Override
    public void onProvideNutritionInfoFailed() {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }
}
