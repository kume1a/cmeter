package com.kumela.cmeter.ui.adapters.search;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.local.list.SearchSuggestionItem;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 01,August,2020
 **/

public interface SearchSuggestionItemViewMvc
        extends ObservableViewMvc<SearchSuggestionItemViewMvc.Listener> {
    interface Listener {
        void onSearchSuggestionItemClicked(@NonNull SearchSuggestionItem searchSuggestionItem);

        void onSearchSuggestionCommitClick(@NonNull String suggestionName);
    }

    void bindSearchSuggestionItem(SearchSuggestionItem searchSuggestionItem);
}
