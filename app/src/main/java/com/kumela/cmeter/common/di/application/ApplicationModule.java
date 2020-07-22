package com.kumela.cmeter.common.di.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.kumela.cmeter.network.api.NutritionXService;
import com.kumela.cmeter.network.api.nutrition.FetchNutritionInfoUseCase;
import com.kumela.cmeter.network.api.search.FetchSearchResultsUseCase;

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

    @Provides
    FetchNutritionInfoUseCase providesFetchNutritionInfoUseCase(NutritionXService nutritionXService) {
        return new FetchNutritionInfoUseCase(nutritionXService);
    }

    @Provides
    Handler providesUiHandler() {
        return new Handler(Looper.getMainLooper());
    }

}
