package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.info;

import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 11,July,2020
 **/

public interface InfoViewMvc extends ObservableViewMvc<InfoViewMvc.Listener> {
    interface Listener {
        void onAgeValueChange(int newValue);

        void onHeightValueChange(int newValue);

        void onMaleClick();

        void onFemaleClick();
    }
}
