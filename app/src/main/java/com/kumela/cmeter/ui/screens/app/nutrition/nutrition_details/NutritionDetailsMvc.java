package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.api.nutrition.AltMeasure;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.local.NutritionDetailItem;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

import java.util.List;

/**
 * Created by Toko on 03,July,2020
 **/

public interface NutritionDetailsMvc extends ObservableViewMvc<NutritionDetailsMvc.Listener> {

    interface Listener {

        void onFabClicked();

        void onAltMeasureChanged(@NonNull AltMeasure altMeasure);

        void onServingQuantityChanged(float servingQuantity);

    }

    void showFab();

    void bindNutritionInfo(NutritionInfo nutritionInfo);

    void bindNutritionDetails(List<NutritionDetailItem> nutritionDetails);

    void updateNutritionInfo(NutritionInfo nutritionInfo);

    void updateNutritionDetails(List<NutritionDetailItem> nutritionDetails);
}
