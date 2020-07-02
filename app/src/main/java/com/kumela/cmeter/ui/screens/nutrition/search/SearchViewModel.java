package com.kumela.cmeter.ui.screens.nutrition.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.kumela.cmeter.model.search.SearchItem;
import com.kumela.cmeter.network.search.FetchSearchResultsUseCase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Toko on 01,July,2020
 **/

public class SearchViewModel extends ViewModel implements FetchSearchResultsUseCase.Listener {

    private static final String TAG = "SearchViewModel";

    interface Listener {
        void onSearchItemsFetched(Set<SearchItem> searchItems);

        void onSearchItemsFetchFailed();
    }

    private Set<Listener> mListeners = new HashSet<>(1);
    private Set<SearchItem> mSearchItems = new HashSet<>();

    private String mQuery = "";

    private final FetchSearchResultsUseCase mFetchSearchResultsUseCase;

    public SearchViewModel(FetchSearchResultsUseCase fetchSearchResultsUseCase) {
        this.mFetchSearchResultsUseCase = fetchSearchResultsUseCase;

        mFetchSearchResultsUseCase.registerListener(this);
    }

    void fetchSearchResultsAndNotify(@NonNull String query) {
        Log.d(TAG, "fetchSearchResultsAndNotify: called, query = " + query + ", mQuery = " + mQuery);
        if (!query.equals(mQuery)) {
            Log.d(TAG, "fetchSearchResultsAndNotify: fetching from use case");
            mFetchSearchResultsUseCase.fetchSearchItemsAndNotify(query);
        } else {
            Log.d(TAG, "fetchSearchResultsAndNotify: getting list from view model");
            onSearchItemsFetched(mSearchItems);
        }
        mQuery = query;
    }

    String getQuery() {
        return mQuery;
    }

    @Override
    public void onSearchItemsFetched(Set<SearchItem> searchItems) {
        mSearchItems = searchItems;
        for (Listener listener : mListeners) {
            listener.onSearchItemsFetched(searchItems);
        }
    }

    @Override
    public void onSearchItemsFetchFailed() {
        for (Listener listener : mListeners) {
            listener.onSearchItemsFetchFailed();
        }
    }

    void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mFetchSearchResultsUseCase.unregisterListener(this);
    }
}
