package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.local.MealModel;
import com.kumela.cmeter.ui.common.base.BaseFragment;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;

/**
 * Created by Toko on 17,July,2020
 **/

public class MealFragment extends BaseFragment
        implements MealViewMvc.Listener, MealViewModel.Listener {

    private MealViewMvc mViewMvc;
    private MealViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(MealViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this, getViewModelFactory()).get(MealViewModel.class);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mViewModel.registerListener(this);

        String mealType = MealFragmentArgs.fromBundle(requireArguments()).getMealType();
        mViewMvc.setupToolbar(((ToolbarHelper) requireActivity()), Constants.MEAL_TYPE_TO_STRING.get(mealType));
        int dailyGoalCalories = MealFragmentArgs.fromBundle(requireArguments()).getGoalCaloriesInDay();

        mViewModel.fetchMealModelAndNotify(mealType, dailyGoalCalories);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mViewModel.unregisterListener(this);
    }

    @Override
    public void onProvideMealModel(MealModel mealModel) {
        mViewMvc.bindMealModel(mealModel);
    }

    @Override
    public void noProductsAdded() {
        Log.d(getClass().getSimpleName(), "noProductsAdded: called");
    }

    @Override
    public void onProvideMealModelFailed() {
        Log.e(getClass().getSimpleName(), "onProvideMealModelFailed: called");
    }
}
