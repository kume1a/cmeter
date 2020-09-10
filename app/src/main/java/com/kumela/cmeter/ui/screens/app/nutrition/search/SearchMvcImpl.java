package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.list.FoodListModel;
import com.kumela.cmeter.ui.adapters.food.FoodAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

import java.util.List;

/**
 * Created by Toko on 01,July,2020
 **/

public class SearchMvcImpl extends BaseObservableViewMvc<SearchMvc.Listener> implements SearchMvc {

    private FoodAdapter mAdapter;
    private ViewMvcFactory mViewMvcFactory;

//    private MaterialButton btnFilter;

    public SearchMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.search_fragment, parent, false));

        this.mViewMvcFactory = viewMvcFactory;
    }

    public void onSearchItemClicked(FoodListModel foodListModel) {
        for (Listener listener : getListeners()) listener.onSearchItemClicked(foodListModel);
    }

    @Override
    public void submitList(@NonNull List<FoodListModel> searchItemList) {
        if (mAdapter == null) {
            RecyclerView rvSearchFood = findViewById(R.id.rv_search);
            rvSearchFood.setHasFixedSize(true);

            mAdapter = new FoodAdapter(this, mViewMvcFactory);

            rvSearchFood.setAdapter(mAdapter);
        }

        mAdapter.submitList(searchItemList);
    }
}
