package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvcImpl;

/**
 * Created by Toko on 09,July,2020
 **/

public class WeightGoalViewMvcImpl extends BaseObservableViewMvcImpl<WeightGoalViewMvc.Listener>
        implements WeightGoalViewMvc {

    public WeightGoalViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.onboarding_fragment_goal_weight, container, false));

        final int maxWeight = 150, minWeight = 40, averageWeight = 60;

        NumberPicker currentWeightPicker = findViewById(R.id.np_onboarding_weight_goal_current_weight);
        NumberPicker goalWeightPicker = findViewById(R.id.np_onboarding_weight_goal_goal_weight);

        currentWeightPicker.setMaxValue(maxWeight);
        currentWeightPicker.setMinValue(minWeight);
        currentWeightPicker.setValue(averageWeight);

        goalWeightPicker.setMaxValue(maxWeight);
        goalWeightPicker.setMinValue(minWeight);
        goalWeightPicker.setValue(averageWeight);
    }
}
