package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import com.kumela.cmeter.model.list.BaseNutrition;
import com.kumela.cmeter.model.list.NutritionDetailItem;
import com.kumela.cmeter.model.api.Photo;
import com.kumela.cmeter.ui.common.mvc.ViewMvc;

import java.util.List;

/**
 * Created by Toko on 03,July,2020
 **/

public interface NutritionDetailsMvc extends ViewMvc {
    void bindRecyclerNutritionInfo(List<NutritionDetailItem> nutritionDetails);

    void bindImage(Photo photo);

    void bindBaseNutritionInfo(BaseNutrition baseNutrition);
}
