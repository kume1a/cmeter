package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.model.local.list.FoodListModel;
import com.kumela.cmeter.ui.common.base.BaseFragment;


public class SearchFragment extends BaseFragment implements SearchMvc.Listener {

    private SearchMvc mViewMvc;
    private SearchViewModel mViewModel;
    private SearchNavController mNavController;

    private String mMeal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(SearchMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavController = getNavControllerFactory().newInstance(SearchNavController.class, mViewMvc.getRootView());
        mViewModel = new ViewModelProvider(this, getViewModelFactory()).get(SearchViewModel.class);

        mViewModel.getSearchItemsLiveData().observe(getViewLifecycleOwner(), foodModelsList -> {
            Log.d(getClass().getSimpleName(), "observe: called");
            mViewMvc.submitList(foodModelsList);
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        SearchFragmentArgs args = SearchFragmentArgs.fromBundle(requireArguments());
        mMeal = args.getMeal();
        String food = args.getFoodName();

        mViewModel.fetchSearchResultsAndNotify(food);

        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onSearchItemClicked(FoodListModel foodListModel) {
        mNavController.actionToFoodDetails(foodListModel.foodId, foodListModel.label, mMeal, foodListModel.measures);
    }
}