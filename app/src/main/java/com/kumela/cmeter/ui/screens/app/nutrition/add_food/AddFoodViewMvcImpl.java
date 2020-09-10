package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.list.SearchSuggestionItem;
import com.kumela.cmeter.ui.adapters.search.SearchSuggestionAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;
import com.kumela.cmeter.ui.common.util.ZoomOutPageTransformer;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.AddFoodPagerAdapter;

import java.util.List;

/**
 * Created by Toko on 26,June,2020
 **/

public class AddFoodViewMvcImpl extends BaseObservableViewMvc<AddFoodViewMvc.Listener>
        implements AddFoodViewMvc, SearchSuggestionAdapter.Listener {

    private final float screenWidth;
    private final float screenHeight;

    private TabLayout mTabLayout;
    private RecyclerView mRvSearch;
    private SearchSuggestionAdapter mSearchSuggestionAdapter;
    private SearchView.SearchAutoComplete mSearchAutoComplete;

    private ViewMvcFactory mViewMvcFactory;

    public AddFoodViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.add_food_fragment, container, false));

        this.mViewMvcFactory = viewMvcFactory;

        mRvSearch = findViewById(R.id.rv_add_food_search);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        screenWidth = (float) displayMetrics.widthPixels;
        screenHeight = (float) displayMetrics.heightPixels;
    }

    @Override
    public void setupViewPager(FragmentManager supportFragmentManager, Lifecycle lifecycle) {
        ViewPager2 viewPager = findViewById(R.id.vp_add_food);
        viewPager.setAdapter(new AddFoodPagerAdapter(
                supportFragmentManager,
                lifecycle
        ));
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        mTabLayout = findViewById(R.id.tabs_add_food);
        new TabLayoutMediator(mTabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_recent);
                    tab.setText(R.string.tabs_recent);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_favorite);
                    tab.setText(R.string.tabs_favorite);
                    break;
            }
        }).attach();
    }

    @Override
    public void startCircularRevealAnimation(int cx, int cy) {
        View v = getRootView();
        int revealAnimationDuration = 1000;

        // Define Circular reveal animation
        // Use the diagonal of the root view
        float finalRadius = (float) Math.sqrt(screenWidth * screenWidth + screenHeight * screenHeight);
        Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 20f, finalRadius);
        anim.setDuration(revealAnimationDuration);
        anim.setInterpolator(new FastOutSlowInInterpolator());

        // define background color value animator
        int startColor = ContextCompat.getColor(getContext(), R.color.colorAccentSecondary);
        int endColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);

        ValueAnimator colorAnimator = new ValueAnimator();
        colorAnimator.setIntValues(startColor, endColor);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(revealAnimationDuration);
        colorAnimator.addUpdateListener(valueAnimator -> v.setBackgroundColor((Integer) valueAnimator.getAnimatedValue()));

        // Start both animations together
        anim.start();
        colorAnimator.start();
    }

    /*@Override
    public void startCircularRevealExitAnimation(int endX, int endY) {

        getRootView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                float initRadius = (float) Math.sqrt(screenWidth * screenWidth + screenHeight * screenHeight);
                Animator anim = ViewAnimationUtils.createCircularReveal(v, endX, endY, initRadius, 0);
                anim.setDuration(animationDuration);
                anim.setInterpolator(new FastOutSlowInInterpolator());

                anim.start();
                getBackgroundColorValueAnimator(v).start();
            }
        });
    }*/

    @Override
    public void setupSearchView(MenuItem searchItem, SearchView searchView) {
        mSearchAutoComplete = searchView.findViewById(R.id.search_src_text);

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                for (Listener listener : getListeners()) {
                    listener.onSearchExpanded();
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (searchItem.isActionViewExpanded()) {
                    for (Listener listener : getListeners()) {
                        listener.onSearchCollapsed();
                    }
                }
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchQueryTextListener(searchView));
    }

    @Override
    public void setSearchText(@NonNull String text) {
        mSearchAutoComplete.setText(text);
    }

    @Override
    public void animateSearchReveal(Toolbar toolbar, boolean containsOverflow, boolean show) {
        toolbar.setCollapseIcon(R.drawable.ic_arrow);
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        int numberOfMenuIcon = 1;
        int centerY = toolbar.getHeight() / 2;
        int width = toolbar.getWidth() -
                (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.action_button_min_width_overflow) : 0) -
                ((getResources().getDimensionPixelSize(R.dimen.action_button_min_width) * numberOfMenuIcon) / 2);

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                toolbar, width, centerY,
                show ? 0f : (float) width,
                show ? (float) width : 0f);

        if (!show) {
            final String toolbarTitle = toolbar.getTitle().toString();
            toolbar.setTitle(null);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    toolbar.setTitle(toolbarTitle);
                    toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }
            });
        }

        int duration = 300;
        circularReveal.setDuration(duration);
        circularReveal.start();
    }

    @Override
    public void revealSuggestionList(boolean show) {
        mRvSearch.setVisibility(show ? View.VISIBLE : View.GONE);
        mTabLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onSearchSuggestionItemClicked(@NonNull SearchSuggestionItem searchSuggestionItem) {
        for (Listener listener : getListeners()) {
            listener.onSearchSuggestionItemClicked(searchSuggestionItem);
        }
    }

    @Override
    public void onSearchSuggestionItemCommitClick(@NonNull String suggestionName) {
        for (Listener listener : getListeners()) {
            listener.onSearchSuggestionItemCommitClicked(suggestionName);
        }
    }

    @Override
    public void updateSearchSuggestionsList(List<SearchSuggestionItem> suggestionList) {
        if (mSearchSuggestionAdapter == null) {
            mRvSearch.setHasFixedSize(true);

            mSearchSuggestionAdapter = new SearchSuggestionAdapter(this, mViewMvcFactory);

            mRvSearch.setAdapter(mSearchSuggestionAdapter);
        }

        mSearchSuggestionAdapter.submitList(suggestionList);
    }

    private final class SearchQueryTextListener implements SearchView.OnQueryTextListener {

        private final SearchView mSearchView;

        private final Handler mTextChangeHandler;
        private Runnable mTextChangeRunnable;

        private SearchQueryTextListener(SearchView searchView) {
            this.mSearchView = searchView;

            mTextChangeHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            mSearchView.clearFocus();
            removeCallback();

            for (Listener listener : getListeners()) {
                listener.onSearchQuerySubmit(query);
            }

            return true;
        }


        @Override
        public boolean onQueryTextChange(String newText) {
            removeCallback();

            mTextChangeRunnable = () -> {
                for (Listener listener : getListeners()) {
                    listener.onSearchQueryChanged(newText);
                }
            };

            mTextChangeHandler.postDelayed(mTextChangeRunnable, 400);
            return false;
        }

        private void removeCallback() {
            if (mTextChangeRunnable != null) {
                mTextChangeHandler.removeCallbacks(mTextChangeRunnable);
            }
        }
    }
}
