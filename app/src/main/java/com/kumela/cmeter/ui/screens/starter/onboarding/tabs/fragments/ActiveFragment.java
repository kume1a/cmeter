package com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kumela.cmeter.R;

/**
 * Created by Toko on 09,July,2020
 **/

public class ActiveFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.onboarding_fragment_activity, container, false);
    }
}
