package com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment;

import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

import java.util.List;

/**
 * Created by Toko on 22,July,2020
 **/

public interface TabViewMvc extends ObservableViewMvc<TabViewMvc.Listener> {

    interface Listener {
        void onProductClicked(SearchItem searchItem);

        void onProductAddClicked(SearchItem searchItem);
    }

    void bindRecyclerViewData(List<SearchItem> searchItems);
}
