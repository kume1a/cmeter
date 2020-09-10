package com.kumela.cmeter.common.di.application;

import android.os.Handler;
import android.os.Looper;

import com.kumela.cmeter.network.api.EdamamApiService;
import com.kumela.cmeter.network.api.food.FetchFoodUseCase;
import com.kumela.cmeter.network.api.nutrients.FetchNutrientsUseCase;
import com.kumela.cmeter.network.api.suggestions.FetchSearchSuggestionsUseCase;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Toko on 29,June,2020
 **/

@Module
public class ApplicationModule {

//    private final Application mApplication;
//
//    public ApplicationModule(Application application) {
//        mApplication = application;
//    }

    @Provides
    FetchFoodUseCase providesFoodUseCase(EdamamApiService edamamApiService) {
        return new FetchFoodUseCase(edamamApiService);
    }

    @Provides
    FetchNutrientsUseCase providesFetchNutrientsUseCase(EdamamApiService edamamApiService) {
        return new FetchNutrientsUseCase(edamamApiService);
    }

    @Provides
    FetchSearchSuggestionsUseCase providesFetchSearchSuggestionsUseCase(EdamamApiService edamamApiService) {
        return new FetchSearchSuggestionsUseCase(edamamApiService);
    }
}
