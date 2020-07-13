package com.kumela.cmeter.ui.adapters.nutrition_details;

import com.kumela.cmeter.model.list.NutritionDetailItem;
import com.kumela.cmeter.ui.common.mvc.ViewMvc;

/**
 * Created by Toko on 03,July,2020
 **/

public interface NutritionDetailsItemMvc extends ViewMvc {

    void bindDetailsItem(NutritionDetailItem item);
}