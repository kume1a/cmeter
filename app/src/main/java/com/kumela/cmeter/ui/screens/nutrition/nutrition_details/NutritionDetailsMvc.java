package com.kumela.cmeter.ui.screens.nutrition.nutrition_details;

import com.kumela.cmeter.model.BaseNutrition;
import com.kumela.cmeter.model.NutritionDetailItem;
import com.kumela.cmeter.model.common.Photo;
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
