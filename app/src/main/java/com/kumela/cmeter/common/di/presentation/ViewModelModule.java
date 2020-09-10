package com.kumela.cmeter.common.di.presentation;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kumela.cmeter.common.di.factory.ViewModelFactory;
import com.kumela.cmeter.network.api.food.FetchFoodUseCase;
import com.kumela.cmeter.network.api.nutrients.FetchNutrientsUseCase;
import com.kumela.cmeter.network.api.suggestions.FetchSearchSuggestionsUseCase;
import com.kumela.cmeter.network.firebase.FirebaseProductManager;
import com.kumela.cmeter.ui.screens.app.AppViewModel;
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
    ViewModelFactory providesViewModelFactory(
            Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(NutritionHomeViewModel.class)
    ViewModel providesNutritionHomeViewModel(String uid, FirebaseFirestore firebaseFirestore) {
        return new NutritionHomeViewModel(uid, firebaseFirestore);
    }

    @Provides
    @IntoMap
    @ViewModelKey(AddFoodViewModel.class)
    ViewModel providesAddFoodViewModel(String uid,
                                       FirebaseFirestore firebaseFirestore,
                                       FirebaseProductManager firebaseProductManager,
                                       FetchSearchSuggestionsUseCase fetchSearchSuggestionsUseCase) {
        return new AddFoodViewModel(
                uid,
                firebaseFirestore,
                firebaseProductManager,
                fetchSearchSuggestionsUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    ViewModel providesSearchViewModel(FetchFoodUseCase fetchFoodUseCase) {
        return new SearchViewModel(fetchFoodUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(NutritionDetailsViewModel.class)
    ViewModel providesFoodDetailsViewModel(FetchNutrientsUseCase fetchNutrientsUseCase,
                                           FirebaseProductManager firebaseProductManager,
                                           FirebaseFirestore firebaseFirestore) {
        return new NutritionDetailsViewModel(
                fetchNutrientsUseCase,
                firebaseProductManager,
                firebaseFirestore
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
    @ViewModelKey(MealViewModel.class)
    ViewModel providesMealViewModel(String uid,
                                    FirebaseFirestore firebaseFirestore) {
        return new MealViewModel(uid, firebaseFirestore);
    }

    @Provides
    @IntoMap
    @ViewModelKey(AppViewModel.class)
    ViewModel providesAppViewModel() {
        return new AppViewModel();
    }
}
