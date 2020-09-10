package com.kumela.cmeter.ui.screens.app.nutrition.search;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.local.list.FoodListModel;
import com.kumela.cmeter.ui.adapters.food.FoodAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

import java.util.List;

/**
 * Created by Toko on 02,July,2020
 **/

public interface SearchMvc extends ObservableViewMvc<SearchMvc.Listener>, FoodAdapter.Listener {
    interface Listener {
        void onSearchItemClicked(FoodListModel foodListModel);
    }

    void submitList(@NonNull List<FoodListModel> searchItemList);
}
