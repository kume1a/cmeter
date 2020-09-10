package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.ui.common.base.BaseFragment;

/**
 * Created by Toko on 17,July,2020
 **/

public class MealFragment extends BaseFragment
        implements MealViewMvc.Listener {

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

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);

        // get args provided from calling fragment
        MealFragmentArgs args = MealFragmentArgs.fromBundle(requireArguments());

        String meal = args.getMeal();
        int dailyGoalCalories = args.getGoalCaloriesInDay();

        mViewModel.fetchMealModelAndNotify(meal, dailyGoalCalories);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    /*@Override
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
    }*/
}
