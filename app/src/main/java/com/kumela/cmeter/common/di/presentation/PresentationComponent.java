package com.kumela.cmeter.common.di.presentation;

import com.kumela.cmeter.common.di.NavControllerFactory;
import com.kumela.cmeter.common.di.ViewModelFactory;
import com.kumela.cmeter.common.di.ViewMvcFactory;
import com.kumela.cmeter.network.search.FetchSearchResultsUseCase;
import com.kumela.cmeter.ui.screens.nutrition.home.NutritionHomeNavController;
import com.kumela.cmeter.ui.screens.nutrition.search.SearchViewModel;

import dagger.Subcomponent;

/**
 * Created by Toko on 29,June,2020
 **/

@Subcomponent(modules = {PresentationModule.class, ViewModelModule.class})
public interface PresentationComponent {
    ViewMvcFactory getViewMvcFactory();
    NavControllerFactory getNavControllerFactory();

    NutritionHomeNavController getHomeNavController();

    ViewModelFactory getViewModelFactory();
}
