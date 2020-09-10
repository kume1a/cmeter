package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.fragment_models.MealModel;
import com.kumela.cmeter.model.local.list.NutritionDetailListModel;
import com.kumela.cmeter.model.local.list.ProductHistoryListModel;
import com.kumela.cmeter.ui.adapters.added_food.ProductHistoryAdapter;
import com.kumela.cmeter.ui.adapters.nutrition_details.NutritionDetailsAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

import java.util.List;

/**
 * Created by Toko on 17,July,2020
 **/

public class MealViewMvcImpl extends BaseObservableViewMvc<MealViewMvc.Listener>
        implements MealViewMvc, ProductHistoryAdapter.Listener {

    private MaterialTextView mTvCarbohydratesCurrent, mTvFatsCurrent, mTvProteinsCurrent;
    private MaterialTextView mTvGoalCaloriesProgress, mTvGoalCarbohydratesProgress, mTvGoalFatsProgress, mTvGoalProteinsProgress;
    private ProgressBar mPbGoalCalories, mPbGoalCarbohydrates, mPbGoalFats, mPbGoalProteins;
    private RecyclerView mRecyclerProducts, mRecyclerNutritionDetails;

    private ViewMvcFactory mViewMvcFactory;

    public MealViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.meal_fragment, container, false));

        this.mViewMvcFactory = viewMvcFactory;

        mTvCarbohydratesCurrent = findViewById(R.id.tv_meal_head_amount_carbs);
        mTvFatsCurrent = findViewById(R.id.tv_meal_head_amount_fats);
        mTvProteinsCurrent = findViewById(R.id.tv_meal_head_amount_proteins);

        mTvGoalCaloriesProgress = findViewById(R.id.tv_meal_goals_calories_progress);
        mTvGoalCarbohydratesProgress = findViewById(R.id.tv_meal_goals_carbohydrates_progress);
        mTvGoalFatsProgress = findViewById(R.id.tv_meal_goals_fats_progress);
        mTvGoalProteinsProgress = findViewById(R.id.tv_meal_goals_proteins_progress);

        mPbGoalCalories = findViewById(R.id.pb_meal_goals_calories);
        mPbGoalCarbohydrates = findViewById(R.id.pb_meal_goals_carbohydrates);
        mPbGoalFats = findViewById(R.id.pb_meal_goals_fats);
        mPbGoalProteins = findViewById(R.id.pb_meal_goals_proteins);

        mRecyclerProducts = findViewById(R.id.recycler_meal_products);
        mRecyclerNutritionDetails = findViewById(R.id.recycler_meal_nutrition_details);
    }

    @Override
    public void bindMealModel(MealModel mealModel) {
        float currentCalories = mealModel.getBaseNutrition().getCalories() - 1;
        float currentCarbohydrates = mealModel.getBaseNutrition().getCarbohydrates() - 1;
        float currentFats = mealModel.getBaseNutrition().getFats() - 1;
        float currentProteins = mealModel.getBaseNutrition().getProteins() - 1;

        int goalCalories = mealModel.getGoalCalories();
        int goalCarbohydrates = mealModel.getGoalCarbohydrates();
        int goalFats = mealModel.getGoalFats();
        int goalProteins = mealModel.getGoalProteins();

        mTvCarbohydratesCurrent.setText(getResources().getString(R.string.value_1f_g, currentCarbohydrates));
        mTvFatsCurrent.setText(getResources().getString(R.string.value_1f_g, currentFats));
        mTvProteinsCurrent.setText(getResources().getString(R.string.value_1f_g, currentProteins));

        mTvGoalCaloriesProgress.setText(getResources().getString(R.string.value_slash_value_cal, (int) currentCalories, goalCalories));
        mTvGoalCarbohydratesProgress.setText(getResources().getString(R.string.value_slash_value_g, (int) currentCarbohydrates, goalCarbohydrates));
        mTvGoalFatsProgress.setText(getResources().getString(R.string.value_slash_value_g, (int) currentFats, goalFats));
        mTvGoalProteinsProgress.setText(getResources().getString(R.string.value_slash_value_g, (int) currentProteins, goalProteins));

        mPbGoalCalories.setMax(goalCalories);
        mPbGoalCarbohydrates.setMax(goalCarbohydrates);
        mPbGoalFats.setMax(goalFats);
        mPbGoalProteins.setMax(goalProteins);

        animateProgressBar(mPbGoalCalories, (int) currentCalories);
        animateProgressBar(mPbGoalCarbohydrates, (int) currentCarbohydrates);
        animateProgressBar(mPbGoalFats, (int) currentFats);
        animateProgressBar(mPbGoalProteins, (int) currentProteins);

        bindNutritionDetails(mealModel.getNutritionDetailListModels());
        bindAddedProducts(mealModel.getProducts());
    }

    private void bindNutritionDetails(List<NutritionDetailListModel> nutritionDetailListModels) {
        NutritionDetailsAdapter nutritionDetailsAdapter = new NutritionDetailsAdapter(mViewMvcFactory);
        nutritionDetailsAdapter.submitList(nutritionDetailListModels);

        mRecyclerNutritionDetails.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerNutritionDetails.setAdapter(nutritionDetailsAdapter);
    }

    private void bindAddedProducts(List<ProductHistoryListModel> products) {
        ProductHistoryAdapter productAdapter = new ProductHistoryAdapter(this, mViewMvcFactory, ProductHistoryAdapter.ADAPTER_TYPE_NONE);

        mRecyclerProducts.setAdapter(productAdapter);
    }

    @Override
    public void onProductClicked(ProductHistoryListModel historyListModel) {
        Log.d(getClass().getSimpleName(), "onProductClicked: historyListModel = " + historyListModel);
    }

    private Interpolator overshootInterpolator = new OvershootInterpolator();

    private void animateProgressBar(ProgressBar progressBar, int value) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, value);
        objectAnimator.setInterpolator(overshootInterpolator);
        objectAnimator.setDuration(1500);
        objectAnimator.start();
    }


}
