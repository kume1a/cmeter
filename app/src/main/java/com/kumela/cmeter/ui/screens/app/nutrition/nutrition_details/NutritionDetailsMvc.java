package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.local.FoodNutrients;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

import java.util.List;

/**
 * Created by Toko on 03,July,2020
 **/

public interface NutritionDetailsMvc extends ObservableViewMvc<NutritionDetailsMvc.Listener> {

    interface Listener {
        void onMeasureChanged(@NonNull Measure measure, float quantity);

        void onServingQuantityChanged(float servingQuantity);
    }

    void bindNutritionInfo(FoodNutrients foodNutrients);

    void bindServingUnitInfo(List<Measure> measures);
}
