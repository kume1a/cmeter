package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;

/**
 * Created by Toko on 17,July,2020
 **/

public class MealViewMvcImpl extends BaseObservableViewMvc<MealViewMvc.Listener>
        implements MealViewMvc  {

    private MaterialTextView mTvCarbohydratesCurrent, mTvFatsCurrent, mTvProteinsCurrent;
    private MaterialTextView mTvGoalCaloriesProgress, mTvGoalCarbohydratesProgress, mTvGoalFatsProgress, mTvGoalProteinsProgress;
    private ProgressBar mPbGoalCalories, mPbGoalCarbohydrates, mPbGoalFats, mPbGoalProteins;
    private RecyclerView mRecyclerProducts, mRecyclerNutritionDetails;

    public MealViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.meal_fragment, container, false));

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
    public void setupToolbar(ToolbarHelper toolbarHelper, int titleResId) {
        toolbarHelper.setTitle(getResources().getString(titleResId));
    }
}
