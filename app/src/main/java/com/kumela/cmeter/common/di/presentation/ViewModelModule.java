package com.kumela.cmeter.common.di.presentation;

import androidx.lifecycle.ViewModel;

import com.kumela.cmeter.common.di.ViewModelFactory;
import com.kumela.cmeter.network.search.FetchSearchResultsUseCase;
import com.kumela.cmeter.ui.screens.nutrition.search.SearchViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

/**
 * Created by Toko on 02,July,2020
 **/

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory providesViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    ViewModel providesSearchViewModel(FetchSearchResultsUseCase fetchSearchResultsUseCase) {
        return new SearchViewModel(fetchSearchResultsUseCase);
    }
}
