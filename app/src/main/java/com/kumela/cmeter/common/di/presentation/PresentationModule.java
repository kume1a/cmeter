package com.kumela.cmeter.common.di.presentation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Toko on 29,June,2020
 **/


@Module
public class PresentationModule {

    private final AppCompatActivity mActivity;

    public PresentationModule(AppCompatActivity appCompatActivity) {
        mActivity = appCompatActivity;
    }

    @Provides
    AppCompatActivity providesActivity() {
        return mActivity;
    }

    @Provides
    LayoutInflater providesLayoutInflater(AppCompatActivity activity) {
        return LayoutInflater.from(activity);
    }

    @Provides
    Context providesContext(AppCompatActivity activity) {
        return activity;
    }
}

