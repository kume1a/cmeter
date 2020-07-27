package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.base.BaseFragment;

public class NutritionHomeFragment extends BaseFragment implements NutritionHomeMvc.Listener {

    private NutritionHomeMvc mViewMvc;
    private NutritionHomeNavController mNavController;
    private NutritionHomeViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(NutritionHomeMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavController = getNavControllerFactory().newInstance(NutritionHomeNavController.class, view);
        mViewModel = new ViewModelProvider(this, getViewModelFactory()).get(NutritionHomeViewModel.class);

        // show fab menu from activity
        View fabMenu = requireActivity().findViewById(R.id.nutrition_home_fab_menu);
        View dim = requireActivity().findViewById(R.id.view_dim);
        mViewMvc.showFabMenu(fabMenu, dim);

        // listen for live data changes from firebase database
        mViewModel.getNutritionHomeModelLiveData().observe(
                getViewLifecycleOwner(),
                nutritionHomeModel -> mViewMvc.bindHomeModelInfo(nutritionHomeModel)
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mViewModel.fetchNutritionHomeInfo();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // hide fab menu from activity
        View fabMenu = requireActivity().findViewById(R.id.nutrition_home_fab_menu);
        mViewMvc.hideFabMenu(fabMenu);
    }

    @Override
    public void onMenuClick(@NonNull View v, @NonNull String meal) {
        mViewMvc.animateViewToCenter(v, meal);

        // disable any touch event on screen until animation is ended
        requireActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        );

        mViewMvc.hideDimmer();
        mViewMvc.hideRestOfMenu(v);
    }

    @Override
    public void onFabAnimationEnded(@NonNull View v, float startX, @NonNull String meal) {
        float cx = v.getX() + (float) v.getWidth() / 2;
        float cy = v.getY() + (float) v.getHeight() / 2;
        
        mNavController.actionToAddFood(meal, cx, cy);
        mViewMvc.resetFabToInitialPosition(v, startX);
    }

    @Override
    public void onFabClick(boolean isMenuOpen) {
        if (isMenuOpen) {
            mViewMvc.closeMenu();
        } else mViewMvc.openMenu();
    }

    @Override
    public void onMealClick(@NonNull String mealType) {
        mNavController.actionToMeal(mealType, mViewModel.getGoalCaloriesInDay());
    }
}