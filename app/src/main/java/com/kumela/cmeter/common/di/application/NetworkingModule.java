package com.kumela.cmeter.common.di.application;

import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.network.NutritionXService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Toko on 29,June,2020
 **/

@Module
public class NetworkingModule {

    @Singleton
    @Provides
    Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    NutritionXService providesNutritionXService(Retrofit retrofit) {
        return retrofit.create(NutritionXService.class);
    }

}