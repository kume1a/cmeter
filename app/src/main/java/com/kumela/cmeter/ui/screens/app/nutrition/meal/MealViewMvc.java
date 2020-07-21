package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import androidx.annotation.StringRes;

import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;

/**
 * Created by Toko on 17,July,2020
 **/

public interface MealViewMvc extends ObservableViewMvc<MealViewMvc.Listener> {
    interface Listener {

    }

    void setupToolbar(ToolbarHelper toolbarHelper, @StringRes int titleResId);
}
