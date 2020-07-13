package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.goal;

/**
 * Created by Toko on 11,July,2020
 **/

import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

public interface GoalViewMvc extends ObservableViewMvc<GoalViewMvc.Listener> {
    interface Listener {
        void onLoseWeightClick();

        void onMaintainWeightClick();

        void onGainWeightClick();
    }
}
