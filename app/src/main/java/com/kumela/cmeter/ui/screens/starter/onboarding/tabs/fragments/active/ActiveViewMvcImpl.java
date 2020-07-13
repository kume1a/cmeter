package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.active;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 11,July,2020
 **/

public class ActiveViewMvcImpl extends BaseObservableViewMvc<ActiveViewMvc.Listener>
        implements ActiveViewMvc {

    public ActiveViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.onboarding_fragment_activity, container, false));

        MaterialRadioButton rbNoActivity = findViewById(R.id.rb_onboarding_activity_no_exercise);
        MaterialRadioButton rbLightActivity = findViewById(R.id.rb_onboarding_activity_light);
        MaterialRadioButton rbModerateActivity = findViewById(R.id.rb_onboarding_activity_moderate);
        MaterialRadioButton rbHeavyActivity = findViewById(R.id.rb_onboarding_activity_heavy);
        MaterialRadioButton rbVeryHeavyActivity = findViewById(R.id.rb_onboarding_activity_very_heavy);

        rbNoActivity.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onNoActivityClick();
        });
        rbLightActivity.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onLightActivityClick();
        });
        rbModerateActivity.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onModerateActivityClick();
        });
        rbHeavyActivity.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onHeavyActivityClick();
        });
        rbVeryHeavyActivity.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onVeryHeavyActivityClick();
        });
    }


}
