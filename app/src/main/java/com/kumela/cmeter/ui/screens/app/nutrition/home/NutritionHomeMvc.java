package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.view.View;

import androidx.annotation.NonNull;

import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 04,July,2020
 **/

public interface NutritionHomeMvc extends BaseObservableViewMvc<NutritionHomeMvc.Listener> {

    void showFabMenu(View v);

    void hideFabMenu(View v);

    interface Listener {

        void onMenuClick(@NonNull String title);

        void onFabClick(boolean isMenuOpen);
    }

    void closeMenu();

    void openMenu();
}
