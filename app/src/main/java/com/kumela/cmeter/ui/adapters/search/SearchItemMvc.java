package com.kumela.cmeter.ui.adapters.search;

import com.kumela.cmeter.model.search.SearchItem;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 02,July,2020
 **/

public interface SearchItemMvc extends BaseObservableViewMvc<SearchItemMvc.Listener> {

    interface Listener {
        void onSearchItemClicked(SearchItem searchItem);
    }

    void bindSearchItem(SearchItem searchItem);
}
