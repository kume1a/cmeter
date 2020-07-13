package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.app.Activity;
import android.view.Menu;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.ui.adapters.search.SearchAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

import java.util.Set;

/**
 * Created by Toko on 02,July,2020
 **/

public interface SearchMvc extends ObservableViewMvc<SearchMvc.Listener>, SearchAdapter.Listener {
    interface Listener {
        void finish();

        void onRequestFetch(String query);

        void onSearchItemClicked(SearchItem searchItem);
    }

    void animateActivity(int x, int y);

    void setupRecyclerView();

    void submitList(@NonNull Set<SearchItem> searchItemList);

    void setupSearchView(Activity activity, Menu menu, String lastQuery);
}
