package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.view.View;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.local.NutritionHomeModel;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 04,July,2020
 **/

public interface NutritionHomeMvc extends ObservableViewMvc<NutritionHomeMvc.Listener> {

    interface Listener {

        void onMenuClick(@NonNull String mealType);

        void onFabClick(boolean isMenuOpen);

        void onMealClick(@NonNull String mealType);
    }

    void showFabMenu(View fabMenu, View dim);

    void hideFabMenu(View fabMenu);

    void closeMenu();

    void openMenu();


    void bindHomeModelInfo(NutritionHomeModel nutritionHomeModel);
}
