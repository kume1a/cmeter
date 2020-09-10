package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import com.kumela.cmeter.model.local.fragment_models.MealModel;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 17,July,2020
 **/

public interface MealViewMvc extends ObservableViewMvc<MealViewMvc.Listener> {

    interface Listener {

    }

    void bindMealModel(MealModel mealModel);
}
