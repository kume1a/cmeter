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
import androidx.appcompat.app.AppCompatActivity;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;
import com.kumela.cmeter.ui.common.base.BaseFragment;

public class AddFoodFragment extends BaseFragment {

    private AddFoodViewMvc mViewMvc;
    private AddFoodNavController mNavController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mViewMvc = getViewMvcFactory().newInstance(AddFoodViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavController = getNavControllerFactory().newInstance(AddFoodNavController.class, requireContext());

        mViewMvc.setupToolbar(requireActivity(), AddFoodFragmentArgs.fromBundle(requireArguments()).getTitle());
        mViewMvc.setupViewPager((AppCompatActivity) requireActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewMvc.resetToolbar(requireActivity());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_food_search) {
            int[] searchPosition = mViewMvc.getSearchPosition(
                    ((ToolbarHelper) requireActivity()).findMenuView(item.getItemId())
            );
            mNavController.actionToSearch(searchPosition[0], searchPosition[1]);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_food, menu);
    }
}