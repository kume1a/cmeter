package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.animation.Animator;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.ui.adapters.search.SearchAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Toko on 01,July,2020
 **/

public class SearchMvcImpl extends BaseObservableViewMvc<SearchMvc.Listener> implements SearchMvc {

    private SearchAdapter mAdapter;
    private ViewMvcFactory mViewMvcFactory;

    public SearchMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.search_activity, parent, false));

        this.mViewMvcFactory = viewMvcFactory;

        setupRecyclerView();
    }

    @Override
    public void animateActivity(int x, int y) {
        View root = findViewById(R.id.root_search);

        root.setVisibility(View.INVISIBLE);

        ViewTreeObserver viewTreeObserver = root.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    float finalRadius = Math.max(root.getWidth(), root.getHeight());

                    Animator circularReveal =
                            ViewAnimationUtils.createCircularReveal(root, x, y, 0, finalRadius);

                    circularReveal.setDuration(800);
                    root.setVisibility(View.VISIBLE);
                    circularReveal.start();

                    root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    @Override
    public void onSearchItemClicked(SearchItem searchItem) {
        for (Listener listener : getListeners()) listener.onSearchItemClicked(searchItem);
    }

    @Override
    public void submitList(@NonNull Set<SearchItem> searchItemSet) {
        mAdapter.submitList(new ArrayList<>(searchItemSet));
    }

    @Override
    public void setupRecyclerView() {
        mAdapter = new SearchAdapter(this, mViewMvcFactory);

        RecyclerView recyclerView = findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setupSearchView(Activity activity, Menu menu, String lastQuery) {
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(activity.getComponentName());
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_search).getActionView();
        searchView.setSearchableInfo(searchableInfo);

        searchView.setQuery(lastQuery, false);
        searchView.setIconified(false);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        ImageView ivClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        ivClose.setImageResource(R.drawable.ic_close);

        TextView tvSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        tvSearch.setTextColor(getResources().getColor(R.color.colorAccent));

        searchView.setOnQueryTextListener(new SearchQueryTextListener(searchView));
        searchView.setOnCloseListener(() -> {
            for (Listener listener : getListeners()) listener.finish();
            return true;
        });
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

            return true;
        }


        @Override
        public boolean onQueryTextChange(String newText) {
            removeCallback();

            if (!newText.isEmpty()) {
                mTextChangeRunnable = () -> {
                    for (Listener listener : getListeners()) listener.onRequestFetch(newText);
                };

                mTextChangeHandler.postDelayed(mTextChangeRunnable, 450);
            } else mAdapter.submitList(new ArrayList<>());

            return false;
        }

        private void removeCallback() {
            if (mTextChangeRunnable != null) {
                mTextChangeHandler.removeCallbacks(mTextChangeRunnable);
            }
        }
    }
}
