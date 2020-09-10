package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.kumela.cmeter.model.local.list.SearchSuggestionItem;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

import java.util.List;

/**
 * Created by Toko on 10,July,2020
 **/

public interface AddFoodViewMvc extends ObservableViewMvc<AddFoodViewMvc.Listener> {

    interface Listener {
        void onSearchExpanded();

        void onSearchCollapsed();

        void onSearchQueryChanged(String newText);

        void onSearchQuerySubmit(String query);

        void onSearchSuggestionItemClicked(@NonNull SearchSuggestionItem searchSuggestionItem);

        void onSearchSuggestionItemCommitClicked(@NonNull String suggestionName);
    }

    void setupViewPager(FragmentManager supportFragmentManager, Lifecycle lifecycle);

    void startCircularRevealAnimation(int cx, int cy);

    void animateSearchReveal(Toolbar toolbar, boolean containsOverflow, boolean show);

    void revealSuggestionList(boolean show);

    void updateSearchSuggestionsList(List<SearchSuggestionItem> suggestions);

    void setupSearchView(MenuItem searchItem, SearchView searchView);

    void setSearchText(@NonNull String text);
}
