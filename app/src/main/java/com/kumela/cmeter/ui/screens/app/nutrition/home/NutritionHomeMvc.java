package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.view.View;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.local.fragment_models.NutritionHomeModel;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 04,July,2020
 **/

public interface NutritionHomeMvc extends ObservableViewMvc<NutritionHomeMvc.Listener> {

    interface Listener {

        void onMenuClick(@NonNull View v, @NonNull String meal);

        void onFabClick(boolean isMenuOpen);

        void onMealClick(@NonNull String meal);

        void onFabAnimationEnded(@NonNull View v, float startX, @NonNull String meal);

        void onLoadingAnimationEnd();

        void onLoadingExitAnimationEnd();
    }

    void showFabMenu(View fabMenu, View dim);

    void hideFabMenu(View fabMenu);

    void closeMenu();

    void openMenu();

    // fab animation to circular reveal
    void animateViewToCenter(@NonNull View v,@NonNull String meal);

    void hideDimmer();

    void hideRestOfMenu(@NonNull View view);

    void resetFabToInitialPosition(View v, float startX);

    // binding info to ui
    void bindHomeModelInfo(NutritionHomeModel nutritionHomeModel);

    // loading animation
    void startLoadingAnimation();

    void startLoadingExitAnimation();

    void revealHomeData();
}
