package com.kumela.cmeter.common.di.presentation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Toko on 29,June,2020
 **/


@Module
public class PresentationModule {

    private final FragmentActivity mActivity;

    public PresentationModule(FragmentActivity fragmentActivity) {
        mActivity = fragmentActivity;
    }

    @Provides
    LayoutInflater providesLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    @Provides
    Activity providesActivity() {
        return mActivity;
    }

    @Provides
    Context providesContext(Activity activity) {
        return activity;
    }
}

