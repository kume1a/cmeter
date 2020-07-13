package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.info;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 11,July,2020
 **/

public class InfoViewMvcImpl extends BaseObservableViewMvc<InfoViewMvc.Listener>
        implements InfoViewMvc {

    public InfoViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.onboarding_fragment_info, container, false));

        AppCompatRadioButton rbMale = findViewById(R.id.rb_onboarding_info_male);
        AppCompatRadioButton rbFemale = findViewById(R.id.rb_onboarding_info_female);

        rbMale.setOnClickListener(v -> {
            for (Listener listener: getListeners()) listener.onMaleClick();
        });
        rbFemale.setOnClickListener(v -> {
            for (Listener listener: getListeners()) listener.onFemaleClick();
        });


        NumberPicker agePicker = findViewById(R.id.np_onboarding_info_age);
        NumberPicker heightPicker = findViewById(R.id.np_onboarding_info_height);

        agePicker.setMinValue(Constants.MIN_AGE);
        agePicker.setMaxValue(Constants.MAX_AGE);
        agePicker.setValue(Constants.AVERAGE_AGE);

        heightPicker.setMinValue(Constants.MIN_HEIGHT);
        heightPicker.setMaxValue(Constants.MAX_HEIGHT);
        heightPicker.setValue(Constants.AVERAGE_HEIGHT);

        agePicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            for (Listener listener : getListeners()) listener.onAgeValueChange(newVal);
        });
        heightPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            for (Listener listener : getListeners()) listener.onHeightValueChange(newVal);
        });
    }
}
