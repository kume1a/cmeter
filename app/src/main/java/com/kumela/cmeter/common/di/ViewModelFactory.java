package com.kumela.cmeter.common.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Provider;

/**
 * Created by Toko on 01,July,2020
 **/

public class ViewModelFactory implements ViewModelProvider.Factory {


    private Map<Class<? extends ViewModel>, Provider<ViewModel>> mProviderMap;

    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        this.mProviderMap = providerMap;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<ViewModel> viewModelProvider = mProviderMap.get(modelClass);
        if (viewModelProvider != null) {
            return (T) viewModelProvider.get();
        } else throw new RuntimeException("Unsupported view model class");
    }
}
