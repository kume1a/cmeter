package com.kumela.cmeter.ui.fragments.diary.add_food;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.R;
import com.kumela.cmeter.model.search.SearchItem;
import com.kumela.cmeter.network.search.SearchHandler;
import com.kumela.cmeter.ui.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends BaseActivity implements SearchHandler.Listener {

    private static final String TAG = "SearchActivity";

    public static final String EXTRA_SEARCH_QUERY = "EXTRA_SEARCH_QUERY";

    private SearchItemAdapter mAdapter;
    private SearchHandler mSearchHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    private void init() {
        mSearchHandler = getCompositionRoot().getSearchHandler();

        setupToolbar();
        setupRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        mSearchHandler.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSearchHandler.unregisterListener(this);
    }

    private void setupRecyclerView() {
        mAdapter = new SearchItemAdapter();

        RecyclerView recyclerView = findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_search);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
            final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_search).getActionView();
            searchView.setSearchableInfo(searchableInfo);

            setupSearchView(searchView);
            searchView.setOnQueryTextListener(new SearchQueryTextListener(searchView));
            searchView.setOnCloseListener(() -> {
                finish();
                return true;
            });
        }
        return true;
    }

    private final class SearchQueryTextListener implements SearchView.OnQueryTextListener {

        private final SearchView mSearchView;

        private final Handler mTextChangeHandler;
        private Runnable mTextChangeRunnable;

        private SearchQueryTextListener(SearchView searchView) {
            this.mSearchView = searchView;

            mTextChangeHandler = new Handler();
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            mSearchView.clearFocus();
            removeCallback();

            Intent intent = getIntent();
            intent.putExtra(EXTRA_SEARCH_QUERY, query);
            setResult(Activity.RESULT_OK, intent);

            finish();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            removeCallback();

            if (!newText.isEmpty()) {
                mTextChangeRunnable = () -> mSearchHandler.fetchSearchItemsAndNotify(newText);
                mTextChangeHandler.postDelayed(mTextChangeRunnable, 500);
            } else {
                mAdapter.submitList(new ArrayList<>());
            }

            return false;
        }

        private void removeCallback() {
            if (mTextChangeRunnable != null) {
                mTextChangeHandler.removeCallbacks(mTextChangeRunnable);
            }
        }
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setIconified(false);

        searchView.setMaxWidth(Integer.MAX_VALUE);

        ImageView ivClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        ivClose.setImageResource(R.drawable.ic_close);

        TextView tvSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        tvSearch.setTextColor(getResources().getColor(R.color.colorAccent));
        tvSearch.setHintTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void onSearchItemsFetched(List<SearchItem> searchItems) {
        mAdapter.submitList(searchItems);
        Log.d(TAG, "onSearchItemsFetched: called searchItems = " + searchItems);
    }

    @Override
    public void onSearchItemsFetchFailed() {
        // TODO: 6/23/2020 notify user that fetch failed
        Log.e(getClass().getSimpleName(), "onSearchItemsFetchFailed: items fetch failed");
    }
}