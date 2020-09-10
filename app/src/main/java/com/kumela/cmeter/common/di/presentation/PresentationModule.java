package com.kumela.cmeter.common.di.presentation;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

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

    @Provides
    Handler providesUiHandler(Context context) {
        return new Handler(context.getMainLooper());
    }
}

