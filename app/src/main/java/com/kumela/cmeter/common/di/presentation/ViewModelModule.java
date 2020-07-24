package com.kumela.cmeter.common.di.presentation;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kumela.cmeter.common.di.factory.ViewModelFactory;
import com.kumela.cmeter.network.api.nutrition.FetchNutritionInfoUseCase;
import com.kumela.cmeter.network.api.search.FetchSearchResultsUseCase;
import com.kumela.cmeter.network.firebase.FirebaseProductManager;
import com.kumela.cmeter.ui.common.util.NutritionInfoParser;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodViewModel;
import com.kumela.cmeter.ui.screens.app.nutrition.home.NutritionHomeViewModel;
import com.kumela.cmeter.ui.screens.app.nutrition.meal.MealViewModel;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsViewModel;
import com.kumela.cmeter.ui.screens.app.nutrition.search.SearchViewModel;
import com.kumela.cmeter.ui.screens.starter.onboarding.OnBoardingViewModel;

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
    ViewModel providesSearchViewModel(FetchSearchResultsUseCase fetchSearchResultsUseCase,
                                      FetchNutritionInfoUseCase fetchNutritionInfoUseCase,
                                      FirebaseProductManager firebaseProductManager) {
        return new SearchViewModel(
                fetchSearchResultsUseCase,
                fetchNutritionInfoUseCase,
                firebaseProductManager
        );
    }

    @Provides
    @IntoMap
    @ViewModelKey(NutritionDetailsViewModel.class)
    ViewModel providesFoodDetailsViewModel(FetchNutritionInfoUseCase fetchNutritionInfoUseCase,
                                           NutritionInfoParser nutritionInfoParser,
                                           FirebaseProductManager firebaseProductManager) {
        return new NutritionDetailsViewModel(
                fetchNutritionInfoUseCase,
                nutritionInfoParser,
                firebaseProductManager
        );
    }

    @Provides
    @IntoMap
    @ViewModelKey(OnBoardingViewModel.class)
    ViewModel providesOnBoardingViewModel(FirebaseFirestore firebaseFirestore, String uid) {
        return new OnBoardingViewModel(firebaseFirestore, uid);
    }

    @Provides
    @IntoMap
    @ViewModelKey(NutritionHomeViewModel.class)
    ViewModel providesNutritionHomeViewModel(String uid, FirebaseFirestore firebaseFirestore) {
        return new NutritionHomeViewModel(uid, firebaseFirestore);
    }

    @Provides
    @IntoMap
    @ViewModelKey(MealViewModel.class)
    ViewModel providesMealViewModel(String uid,
                                    FirebaseFirestore firebaseFirestore,
                                    NutritionInfoParser nutritionInfoParser) {
        return new MealViewModel(uid, firebaseFirestore, nutritionInfoParser);
    }

    @Provides
    @IntoMap
    @ViewModelKey(AddFoodViewModel.class)
    ViewModel providesAddFoodViewModel(String uid,
                                       FirebaseFirestore firebaseFirestore,
                                       FetchNutritionInfoUseCase fetchNutritionInfoUseCase,
                                       FirebaseProductManager firebaseProductManager) {
        return new AddFoodViewModel(
                uid,
                firebaseFirestore,
                fetchNutritionInfoUseCase,
                firebaseProductManager
        );
    }
}
