package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.base.BaseFragment;

public class NutritionHomeFragment extends BaseFragment implements NutritionHomeMvc.Listener {

    private NutritionHomeMvc mViewMvc;
    private NutritionHomeNavController mNavController;

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

        View fabMenu = requireActivity().findViewById(R.id.nutrition_home_fab_menu);
        View dim = requireActivity().findViewById(R.id.view_dim);
        mViewMvc.showFabMenu(fabMenu, dim);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        View fabMenu = requireActivity().findViewById(R.id.nutrition_home_fab_menu);
        mViewMvc.hideFabMenu(fabMenu);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.nutrition, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.menu_nutrition_stats) {
//            Toast.makeText(this, "Statistics", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onMenuClick(@NonNull String title) {
        mViewMvc.closeMenu();
        mNavController.actionToAddFood(title);
    }

    @Override
    public void onFabClick(boolean isMenuOpen) {
        if (isMenuOpen) {
            mViewMvc.closeMenu();
        } else mViewMvc.openMenu();
    }
}