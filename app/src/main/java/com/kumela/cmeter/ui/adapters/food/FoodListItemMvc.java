package com.kumela.cmeter.ui.adapters.food;

import com.kumela.cmeter.model.local.list.FoodListModel;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 02,July,2020
 **/

public interface FoodListItemMvc extends ObservableViewMvc<FoodListItemMvc.Listener> {

    interface Listener {
        void onSearchItemClicked(FoodListModel foodListModel);
    }

    void bindSearchItem(FoodListModel foodListModel);
}
