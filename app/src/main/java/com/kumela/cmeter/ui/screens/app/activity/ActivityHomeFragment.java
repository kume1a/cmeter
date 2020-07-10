package com.kumela.cmeter.ui.screens.app.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.base.BaseFragment;

/**
 * Created by Toko on 30,June,2020
 **/

public class ActivityHomeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_fragment, container, false);
    }
}