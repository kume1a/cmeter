package com.kumela.cmeter.ui.adapters.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.model.local.list.SearchSuggestionItem;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 01,August,2020
 **/

public class SearchSuggestionItemViewMvcImpl extends BaseObservableViewMvc<SearchSuggestionItemViewMvc.Listener>
        implements SearchSuggestionItemViewMvc {

    private final AppCompatImageView mIvIndicator;
    private final MaterialTextView mTvSuggestion;
    private final AppCompatImageButton mBtnCommit;

    private SearchSuggestionItem mCurrentItem;

    public SearchSuggestionItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_search_suggestion, parent, false));

        this.mIvIndicator = findViewById(R.id.iv_search_suggestion_indicator);
        this.mTvSuggestion = findViewById(R.id.tv_search_suggestion);
        this.mBtnCommit = findViewById(R.id.btn_search_suggestion_commit);

        getRootView().setOnClickListener(v -> {
            for (Listener listener: getListeners()) {
                listener.onSearchSuggestionItemClicked(mCurrentItem);
            }
        });
    }

    @Override
    public void bindSearchSuggestionItem(SearchSuggestionItem searchSuggestionItem) {
        this.mCurrentItem = searchSuggestionItem;

        mIvIndicator.setImageResource(searchSuggestionItem.getDrawableId());
        mTvSuggestion.setText(searchSuggestionItem.getSuggestionName());

        mBtnCommit.setOnClickListener(v -> {
            for (Listener listener: getListeners()) {
                listener.onSearchSuggestionCommitClick(mCurrentItem.getSuggestionName());
            }
        });
    }
}
