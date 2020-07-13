package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 09,July,2020
 **/

public class WeightGoalViewMvcImpl extends BaseObservableViewMvc<WeightGoalViewMvc.Listener>
        implements WeightGoalViewMvc {

    private MaterialRadioButton rb25, rb50, rb75;
    private NumberPicker mGoalWeightPicker;
    private MaterialTextView mTvGoalHeader;

    public WeightGoalViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.onboarding_fragment_goal_weight, container, false));

        rb25 = findViewById(R.id.rb_onboarding_goal_weight_25);
        rb50 = findViewById(R.id.rb_onboarding_goal_weight_50);
        rb75 = findViewById(R.id.rb_onboarding_goal_weight_75);

        rb25.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.on25CLick();
        });
        rb50.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.on50CLick();
        });
        rb75.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.on75CLick();
        });


        NumberPicker currentWeightPicker = findViewById(R.id.np_onboarding_weight_goal_current_weight);
        mGoalWeightPicker = findViewById(R.id.np_onboarding_weight_goal_goal_weight);
        mTvGoalHeader = findViewById(R.id.tv_onboarding_weight_goal_goal_weight);
        
        currentWeightPicker.setMaxValue(Constants.MAX_WEIGHT);
        currentWeightPicker.setMinValue(Constants.MIN_WEIGHT);
        currentWeightPicker.setValue(Constants.AVERAGE_WEIGHT);

        mGoalWeightPicker.setMaxValue(Constants.MAX_WEIGHT);
        mGoalWeightPicker.setMinValue(Constants.MIN_WEIGHT);
        mGoalWeightPicker.setValue(Constants.AVERAGE_WEIGHT);

        currentWeightPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            for (Listener listener: getListeners()) listener.onCurrentWeightValueChanged(newVal);
        });
        mGoalWeightPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            for (Listener listener: getListeners()) listener.onGoalWeightValueChanged(newVal);
        });
    }

    @Override
    public void onGoalChangedToLoseWeight() {
        revealRadioButtons();
        mGoalWeightPicker.setVisibility(View.VISIBLE);
        mTvGoalHeader.setVisibility(View.VISIBLE);

        rb25.setText(getResources().getString(R.string.onboarding_goal_weight_lose_o1));
        rb50.setText(getResources().getString(R.string.onboarding_goal_weight_lose_o2));
        rb75.setText(getResources().getString(R.string.onboarding_goal_weight_lose_o3));
    }

    @Override
    public void onGoalChangedToMaintainWeight() {
        mGoalWeightPicker.setVisibility(View.GONE);
        mTvGoalHeader.setVisibility(View.GONE);

        rb25.setVisibility(View.GONE);
        rb50.setVisibility(View.GONE);
        rb75.setVisibility(View.GONE);
    }

    @Override
    public void onGoalChangedToGainWeight() {
        revealRadioButtons();
        mGoalWeightPicker.setVisibility(View.VISIBLE);
        mTvGoalHeader.setVisibility(View.VISIBLE);

        rb25.setText(getResources().getString(R.string.onboarding_goal_weight_gain_o1));
        rb50.setText(getResources().getString(R.string.onboarding_goal_weight_gain_o2));
        rb75.setText(getResources().getString(R.string.onboarding_goal_weight_gain_o3));
    }

    private void revealRadioButtons() {
        if (rb25.getVisibility() != View.VISIBLE) {
            rb25.setVisibility(View.VISIBLE);
            rb25.setChecked(true);
            rb25.callOnClick();
        }
        if (rb50.getVisibility() != View.VISIBLE) rb50.setVisibility(View.VISIBLE);
        if (rb75.getVisibility() != View.VISIBLE) rb75.setVisibility(View.VISIBLE);
    }
}
