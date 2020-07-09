package com.kumela.cmeter.common.di.application;

import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.network.api.NutritionXService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Toko on 29,June,2020
 **/

@Module
public abstract class NetworkingModule {

    @Singleton
    @Provides
    static Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static NutritionXService providesNutritionXService(Retrofit retrofit) {
        return retrofit.create(NutritionXService.class);
    }

}