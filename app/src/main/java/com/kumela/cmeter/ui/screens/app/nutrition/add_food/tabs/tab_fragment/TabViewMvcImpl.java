package com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.ui.adapters.search.SearchAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

import java.util.List;

/**
 * Created by Toko on 22,July,2020
 **/

public class TabViewMvcImpl extends BaseObservableViewMvc<TabViewMvc.Listener>
        implements TabViewMvc, SearchAdapter.Listener {

    private final ViewMvcFactory mViewMvcFactory;

    private RecyclerView mRecyclerView;

    public TabViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.add_food_tab, container, false));

        this.mViewMvcFactory = viewMvcFactory;

        mRecyclerView = findViewById(R.id.rv_tab_add_food);
    }

    @Override
    public void bindRecyclerViewData(List<SearchItem> searchItems) {
        SearchAdapter searchAdapter = new SearchAdapter(this, mViewMvcFactory);
        searchAdapter.submitList(searchItems);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onSearchItemClicked(SearchItem searchItem) {
        for (Listener listener : getListeners()) listener.onProductClicked(searchItem);
    }

    @Override
    public void onAddButtonClicked(SearchItem searchItem) {
        for (Listener listener: getListeners()) listener.onProductAddClicked(searchItem);
    }
}
