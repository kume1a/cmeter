package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.databinding.MealFragmentBinding;
import com.kumela.cmeter.ui.common.base.BaseFragment;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;

/**
 * Created by Toko on 17,July,2020
 **/

public class MealFragment extends BaseFragment {

    private MealViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(MealViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String mealType = MealFragmentArgs.fromBundle(requireArguments()).getMealType();
        mViewMvc.setupToolbar(((ToolbarHelper) requireActivity()), Constants.MEAL_TYPE_TO_STRING.get(mealType));
    }
}
