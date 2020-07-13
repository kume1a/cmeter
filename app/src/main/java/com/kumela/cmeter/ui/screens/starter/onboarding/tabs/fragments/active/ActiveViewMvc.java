package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.active;

import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 11,July,2020
 **/

public interface ActiveViewMvc extends ObservableViewMvc<ActiveViewMvc.Listener> {
    interface Listener {
        void onNoActivityClick();

        void onLightActivityClick();

        void onModerateActivityClick();

        void onHeavyActivityClick();

        void onVeryHeavyActivityClick();
    }
}
