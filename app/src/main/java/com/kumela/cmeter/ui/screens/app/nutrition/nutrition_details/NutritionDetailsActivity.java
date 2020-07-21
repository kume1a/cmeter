package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.R;
import com.kumela.cmeter.model.api.nutrition.AltMeasure;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.local.NutritionDetailItem;
import com.kumela.cmeter.ui.common.base.BaseActivity;

import java.util.List;

/**
 * Created by Toko on 30,June,2020
 **/

public class NutritionDetailsActivity extends BaseActivity
        implements NutritionDetailsViewModel.Listener, NutritionDetailsMvc.Listener {

    public static final String EXTRA_FOOD_NAME = "EXTRA_FOOD_NAME";
    public static final String EXTRA_MEAL_TYPE = "EXTRA_MEAL_TYPE";

    private NutritionDetailsMvc mViewMvc;
    private NutritionDetailsViewModel mViewModel;
    private String mMealType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMealType = getIntent().getStringExtra(EXTRA_MEAL_TYPE);

        mViewMvc = getViewMvcFactory().newInstance(NutritionDetailsMvc.class, null);

        mViewModel = new ViewModelProvider(
                this,
                getViewModelFactory()
        ).get(NutritionDetailsViewModel.class);

        setContentView(mViewMvc.getRootView());
        setupToolbar(R.id.toolbar_food_details, getFoodName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mViewModel.registerListener(this);
        mViewModel.fetchNutritionInfoAndNotify(getFoodName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
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
    public void onFabClicked() {
        mViewModel.writeProduct(mMealType);
    }

    @Override
    public void onAltMeasureChanged(@NonNull AltMeasure altMeasure) {
        mViewModel.setAltMeasure(altMeasure);
    }

    @Override
    public void onServingQuantityChanged(float servingQuantity) {
        mViewModel.setQuantity(servingQuantity);
    }

    @Override
    public void onProvideNutritionInfo(NutritionInfo nutritionInfo) {
        mViewMvc.bindNutritionInfo(nutritionInfo);
    }

    @Override
    public void onProvideNutritionInfoFailed() {
        // TODO: 7/19/2020 implement failure ui
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProvideNutritionDetails(List<NutritionDetailItem> nutritionDetails) {
        mViewMvc.bindNutritionDetails(nutritionDetails);
        mViewMvc.showFab();
    }

    @Override
    public void onNutritionInfoUpdated(NutritionInfo nutritionInfo) {
        if (nutritionInfo.zeroedOut) {
            mViewMvc.updateNutritionInfo(nutritionInfo.getZeroedOutInstance());
        } else mViewMvc.updateNutritionInfo(nutritionInfo);
    }

    @Override
    public void onNutritionDetailsUpdated(List<NutritionDetailItem> nutritionDetails) {
        mViewMvc.updateNutritionDetails(nutritionDetails);
    }
}
