package com.kumela.cmeter.common.di.application;

import android.app.Application;

import com.kumela.cmeter.network.NutritionXService;
import com.kumela.cmeter.network.search.FetchSearchResultsUseCase;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Toko on 29,June,2020
 **/

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    FetchSearchResultsUseCase providesFetchQuestionsListUseCase(NutritionXService nutritionXService) {
        return new FetchSearchResultsUseCase(nutritionXService);
    }

}
