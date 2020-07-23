package com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.ui.common.base.BaseFragment;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodViewModel;

import java.util.List;

/**
 * Created by Toko on 30,June,2020
 **/

public class TabFragment extends BaseFragment implements TabViewMvc.Listener {

    public static final String EXTRA_TAB_TYPE = "EXTRA_TAB_TYPE";

    public enum TabType {
        RECENT, FAVORITES
    }

    private TabType mTabType;

    private AddFoodViewModel mViewModel;
    private TabViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(TabViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this, getViewModelFactory()).get(AddFoodViewModel.class);
        mTabType = (TabType) requireArguments().getSerializable(EXTRA_TAB_TYPE);

        Observer<? super List<SearchItem>> binder = searchItems -> mViewMvc.bindRecyclerViewData(searchItems);
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
        switch (mTabType) {
            case RECENT:
                mViewModel.getRecentLiveData().observe(lifecycleOwner, binder);
                break;
            case FAVORITES:
                mViewModel.getFavoritesLiveData().observe(lifecycleOwner, binder);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);

        switch (mTabType) {
            case RECENT:
                mViewModel.fetchRecentlyAddedProducts();
                break;
            case FAVORITES:
                mViewModel.fetchFavorites();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }
}
