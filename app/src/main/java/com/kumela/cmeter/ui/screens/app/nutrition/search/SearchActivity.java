package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.R;
import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.ui.common.base.BaseActivity;

import java.util.Set;

public class SearchActivity extends BaseActivity implements SearchViewModel.Listener, SearchMvc.Listener {

    public static final String EXTRA_SEARCH_X = "EXTRA_SEARCH_X";
    public static final String EXTRA_SEARCH_Y = "EXTRA_SEARCH_Y";
    public static final String EXTRA_MEAL_TYPE = "EXTRA_MEAL_TYPE";

    private SearchMvc mViewMvc;
    private SearchViewModel mViewModel;
    private SearchNavController mNavController;

    private String mMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMeal = getIntent().getStringExtra(EXTRA_MEAL_TYPE);

        mViewMvc = getViewMvcFactory().newInstance(SearchMvc.class, null);
        mNavController = getNavControllerFactory().newInstance(SearchNavController.class);
        mViewModel = new ViewModelProvider(
                this,
                getPresentationComponent().getViewModelFactory()
        ).get(SearchViewModel.class);
        onSearchItemsFetched(mViewModel.getSearchItems());

        overridePendingTransition(0,R.anim.slide_out_right);
        if (savedInstanceState == null) {
            mViewMvc.animateActivity(getExtra(EXTRA_SEARCH_X), getExtra(EXTRA_SEARCH_Y));
        }

        setContentView(mViewMvc.getRootView());
        setupToolbar(R.id.toolbar_search, R.string.title_search);
    }

    private int getExtra(String extra) {
        Intent intent = getIntent();
        return intent.getIntExtra(extra, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.registerListener(this);
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.unregisterListener(this);
        mViewMvc.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        mViewMvc.setupSearchView(this, menu, mViewModel.getQuery());
        return true;
    }

    @Override
    public void onSearchItemsFetched(Set<SearchItem> searchItems) {
        mViewMvc.submitList(searchItems);
    }

    @Override
    public void onSearchItemsFetchFailed() {
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestFetch(String query) {
        mViewModel.fetchSearchResultsAndNotify(query);
    }

    @Override
    public void onSearchItemAddButtonClicked(SearchItem searchItem) {
        mViewModel.writeProduct(searchItem.foodName, mMeal);
    }

    @Override
    public void onSearchItemClicked(SearchItem searchItem) {
        mNavController.actionToFoodDetails(searchItem.foodName, mMeal);
    }
}