package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.local.list.SearchSuggestionItem;
import com.kumela.cmeter.ui.common.base.BaseFragment;
import com.kumela.cmeter.ui.screens.app.AppViewModel;

public class AddFoodFragment extends BaseFragment implements AddFoodViewMvc.Listener {

    private AddFoodViewMvc mViewMvc;
    private AddFoodViewModel mViewModel;
    private AddFoodNavController mNavController;

    private String mMeal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mViewMvc = getViewMvcFactory().newInstance(AddFoodViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        enableWindowTouchEvents();

        AddFoodFragmentArgs args = AddFoodFragmentArgs.fromBundle(requireArguments());
        mMeal = args.getMeal();

        // play reveal animation for first time only
        if (savedInstanceState == null) {
            AppViewModel appViewModel = new ViewModelProvider(requireActivity(), getViewModelFactory()).get(AppViewModel.class);
            if (appViewModel.getPlayAddFoodAnimation()) {
                mViewMvc.startCircularRevealAnimation((int) args.getCx(), (int) args.getCy());
                appViewModel.setPlayAddFoodAnimation(false);
            }
        }

        // initialize navigation controller and view model
        mNavController = getNavControllerFactory().newInstance(AddFoodNavController.class, mViewMvc.getRootView());
        mViewModel = new ViewModelProvider(this, getViewModelFactory()).get(AddFoodViewModel.class);

        // observe search suggestions from view model live data
        mViewModel.getSuggestionsLiveData().observe(getViewLifecycleOwner(), suggestions -> mViewMvc.updateSearchSuggestionsList(suggestions));

        mViewMvc.setupViewPager(getChildFragmentManager(), getLifecycle());
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_food, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_add_food_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        mViewMvc.setupSearchView(searchItem, searchView);
    }

    @Override
    public void onSearchExpanded() {
        mViewMvc.animateSearchReveal(getToolbar(), true, true);
        mViewMvc.revealSuggestionList(true);
        disableToolbarScrollingBehavior();

        mViewModel.getSearchHistory(searchHistory -> mViewMvc.updateSearchSuggestionsList(searchHistory));
    }

    @Override
    public void onSearchCollapsed() {
        mViewMvc.animateSearchReveal(getToolbar(), false, false);
        mViewMvc.revealSuggestionList(false);
        enableToolbarScrollingBehavior();
    }

    @Override
    public void onSearchQueryChanged(String newText) {
        if (newText.isEmpty()) {
            mViewModel.getSearchHistory(searchHistory -> mViewMvc.updateSearchSuggestionsList(searchHistory));
        } else mViewModel.fetchSearchSuggestions(newText, 20);
    }

    @Override
    public void onSearchQuerySubmit(String query) {
        saveQueryInHistoryAndNavigateToNextScreen(query);
    }

    @Override
    public void onSearchSuggestionItemClicked(@NonNull SearchSuggestionItem searchSuggestionItem) {
        saveQueryInHistoryAndNavigateToNextScreen(searchSuggestionItem.getSuggestionName());
    }

    private void saveQueryInHistoryAndNavigateToNextScreen(String suggestionName) {
        // write clicked suggestion into database
        mViewModel.writeSearchSuggestion(suggestionName);

        // reset background toolbar color
        getToolbar().setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        // navigate to search food
        mNavController.actionToSearch(Utils.getMealTitle(requireContext(), mMeal), mMeal, suggestionName);

        enableToolbarScrollingBehavior();
    }

    @Override
    public void onSearchSuggestionItemCommitClicked(@NonNull String suggestionName) {
        mViewMvc.setSearchText(suggestionName);
    }
}