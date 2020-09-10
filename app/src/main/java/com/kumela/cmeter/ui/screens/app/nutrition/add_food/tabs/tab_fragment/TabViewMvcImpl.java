package com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.list.ProductHistoryListModel;
import com.kumela.cmeter.ui.adapters.added_food.ProductHistoryAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toko on 22,July,2020
 **/

public class TabViewMvcImpl extends BaseObservableViewMvc<TabViewMvc.Listener>
        implements TabViewMvc, ProductHistoryAdapter.Listener {

    private final ViewMvcFactory mViewMvcFactory;

    private RecyclerView mRecyclerView;
    private ProductHistoryAdapter mRecentAdapter;
    private ProductHistoryAdapter mFavoritesAdapter;

    public TabViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.add_food_tab, container, false));

        this.mViewMvcFactory = viewMvcFactory;

        mRecyclerView = findViewById(R.id.rv_tab_add_food);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void bindHistoryProducts(List<ProductHistoryListModel> productHistory, TabFragment.TabType tabType) {
        if (tabType == TabFragment.TabType.RECENT) {
            if (mRecentAdapter == null) {
                mRecentAdapter = new ProductHistoryAdapter(this, mViewMvcFactory, ProductHistoryAdapter.ADAPTER_TYPE_RECENT);
                mRecyclerView.setAdapter(mRecentAdapter);
            }
            mRecentAdapter.submitList(productHistory);
        } else {
            if (mFavoritesAdapter == null) {
                mFavoritesAdapter = new ProductHistoryAdapter(this, mViewMvcFactory, ProductHistoryAdapter.ADAPTER_TYPE_FAVORITES);
                mRecyclerView.setAdapter(mFavoritesAdapter);
            }
            List<ProductHistoryListModel> filteredList = new ArrayList<>();
            for (ProductHistoryListModel listModel: productHistory) {
                if (listModel.isFavorite()) filteredList.add(listModel);
            }
            mFavoritesAdapter.submitList(filteredList);
        }
    }

    @Override
    public void onProductClicked(ProductHistoryListModel historyListModel) {
        for (Listener listener : getListeners()) {
            listener.onProductClicked(historyListModel);
        }
    }
}
