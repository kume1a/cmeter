package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal;

import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 09,July,2020
 **/

public interface WeightGoalViewMvc extends ObservableViewMvc<WeightGoalViewMvc.Listener> {
    interface Listener {
        void on25CLick();

        void on50CLick();

        void on75CLick();

        void onCurrentWeightValueChanged(int newValue);

        void onGoalWeightValueChanged(int newValue);
    }

    void onGoalChangedToLoseWeight();

    void onGoalChangedToMaintainWeight();

    void onGoalChangedToGainWeight();
}
