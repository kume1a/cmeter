package com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.model.local.list.ProductHistoryListModel;
import com.kumela.cmeter.ui.common.base.BaseFragment;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodViewModel;

import java.util.List;

/**
 * Created by Toko on 30,June,2020
 **/

public class TabFragment extends BaseFragment
        implements TabViewMvc.Listener, AddFoodViewModel.Listener {

    public static final String EXTRA_TAB_TYPE = "EXTRA_TAB_TYPE";
    public static final String EXTRA_MEAL = "EXTRA_MEAL";

    public enum TabType {
        RECENT, FAVORITES
    }

    private TabType mTabType;

    private AddFoodViewModel mViewModel;
    private TabViewMvc mViewMvc;
//    private TabNavController mNavController;

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
//        mNavController = getNavControllerFactory().newInstance(TabNavController.class, mViewMvc.getRootView());

        // get arguments from intent
        Bundle args = requireArguments();
        mTabType = (TabType) args.getSerializable(EXTRA_TAB_TYPE);
    }

    @Override
    public void onStart() {
        super.onStart();

        mViewMvc.registerListener(this);
        mViewModel.registerListener(this);

        mViewModel.fetchProductHistoryAndNotify();
    }

    @Override
    public void onStop() {
        super.onStop();

        mViewMvc.unregisterListener(this);
        mViewModel.unregisterListener(this);
    }

    @Override
    public void onProvideProductHistory(List<ProductHistoryListModel> productHistory) {
        mViewMvc.bindHistoryProducts(productHistory, mTabType);
    }

    @Override
    public void onProvideProductHistoryFailed() {
        Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
        // TODO: 8/4/2020 implement
    }

    @Override
    public void onProductClicked(ProductHistoryListModel foodListModel) {
        // TODO: 8/7/2020 implement navigation
        Log.d(getClass().getSimpleName(), "onProductClicked: foodListModel = " + foodListModel);
    }
}
