package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.local.fragment_models.NutritionHomeModel;
import com.kumela.cmeter.ui.common.base.BaseFragment;

public class NutritionHomeFragment extends BaseFragment
        implements NutritionHomeMvc.Listener, NutritionHomeViewModel.Listener {

    private NutritionHomeMvc mViewMvc;
    private NutritionHomeNavController mNavController;
    private NutritionHomeViewModel mViewModel;

    private boolean mDataAvailable = false;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mViewModel.registerListener(this);

        // fetch model info
        mViewModel.fetchNutritionHomeInfo();
        mViewMvc.startLoadingAnimation();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mViewModel.unregisterListener(this);
    }

    @Override
    public void onProvideNutritionHomeModel(NutritionHomeModel nutritionHomeModel) {
        mViewMvc.bindHomeModelInfo(nutritionHomeModel);
        mDataAvailable = true;
    }

    @Override
    public void onProvideNutritionHomeModelFailed() {
        Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuClick(@NonNull View v, @NonNull String meal) {
        mViewMvc.animateViewToCenter(v, meal);

        // disable any touch event on screen until animation is ended
        disableWindowTouchEvents();

        mViewMvc.hideDimmer();
        mViewMvc.hideRestOfMenu(v);
    }

    @Override
    public void onFabAnimationEnded(@NonNull View v, float startX, @NonNull String meal) {
        float cx = v.getX() + (float) v.getWidth() / 2;
        float cy = v.getY() + (float) v.getHeight() / 2;

        mNavController.actionToAddFood(Utils.getMealTitle(requireContext(), meal), meal, cx, cy);
        mViewMvc.resetFabToInitialPosition(v, startX);
    }

    @Override
    public void onFabClick(boolean isMenuOpen) {
        if (isMenuOpen) {
            mViewMvc.closeMenu();
        } else mViewMvc.openMenu();
    }

    @Override
    public void onMealClick(@NonNull String meal) {
        mNavController.actionToMeal(Utils.getMealTitle(requireContext(), meal), meal, mViewModel.getGoalCaloriesInDay());
    }

    @Override
    public void onLoadingAnimationEnd() {
        if (mDataAvailable) {
            mViewMvc.startLoadingExitAnimation();
        } else mViewMvc.startLoadingAnimation();
    }

    @Override
    public void onLoadingExitAnimationEnd() {
        // show fab menu from activity
        View fabMenu = requireActivity().findViewById(R.id.nutrition_home_fab_menu);
        View dim = requireActivity().findViewById(R.id.view_dim);
        mViewMvc.showFabMenu(fabMenu, dim);

        mViewMvc.revealHomeData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // hide fab menu from activity
        View fabMenu = requireActivity().findViewById(R.id.nutrition_home_fab_menu);
        mViewMvc.hideFabMenu(fabMenu);
    }
}