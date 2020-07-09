package com.kumela.cmeter.common.di.presentation;

import androidx.lifecycle.ViewModel;

import com.kumela.cmeter.common.di.factory.ViewModelFactory;
import com.kumela.cmeter.network.api.nutrition.FetchNutritionInfoUseCase;
import com.kumela.cmeter.network.api.search.FetchSearchResultsUseCase;
import com.kumela.cmeter.ui.common.NutritionInfoParser;
import com.kumela.cmeter.ui.screens.nutrition.nutrition_details.NutritionDetailsViewModel;
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

    @Provides
    @IntoMap
    @ViewModelKey(NutritionDetailsViewModel.class)
    ViewModel providesFoodDetailsViewModel(FetchNutritionInfoUseCase fetchNutritionInfoUseCase, NutritionInfoParser nutritionInfoParser) {
        return new NutritionDetailsViewModel(fetchNutritionInfoUseCase, nutritionInfoParser);
    }
}