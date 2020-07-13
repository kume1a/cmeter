package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.goal;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 11,July,2020
 **/

public class GoalViewMvcImpl extends BaseObservableViewMvc<GoalViewMvc.Listener>
        implements GoalViewMvc {

    public GoalViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.onboarding_fragment_goal, container, false));

        MaterialRadioButton rbLoseWeight = findViewById(R.id.rb_onboarding_goal_lose_weight);
        MaterialRadioButton rbMaintainWeight = findViewById(R.id.rb_onboarding_goal_maintain_weight);
        MaterialRadioButton rbGainWeight = findViewById(R.id.rb_onboarding_goal_gain_weight);

        rbLoseWeight.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onLoseWeightClick();
        });
        rbMaintainWeight.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onMaintainWeightClick();
        });
        rbGainWeight.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onGainWeightClick();
        });
    }
}
