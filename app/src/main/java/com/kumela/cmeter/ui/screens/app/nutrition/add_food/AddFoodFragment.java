package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.ui.common.base.BaseFragment;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;

public class AddFoodFragment extends BaseFragment {

    private AddFoodViewMvc mViewMvc;
    private AddFoodNavController mNavController;

    private String mMealType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mViewMvc = getViewMvcFactory().newInstance(AddFoodViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMealType = AddFoodFragmentArgs.fromBundle(requireArguments()).getMealType();

        mNavController = getNavControllerFactory().newInstance(AddFoodNavController.class);

        mViewMvc.setupToolbar(requireActivity(), Constants.MEAL_TYPE_TO_STRING.get(mMealType));
        mViewMvc.setupViewPager(getChildFragmentManager(), getLifecycle(), mMealType);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_food_search) {
            int[] searchPosition = mViewMvc.getSearchPosition(
                    ((ToolbarHelper) requireActivity()).findMenuView(item.getItemId())
            );
            mNavController.actionToSearch(mMealType, searchPosition[0], searchPosition[1]);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_food, menu);
    }
}