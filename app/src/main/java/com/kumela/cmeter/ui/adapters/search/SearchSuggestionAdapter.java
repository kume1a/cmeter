package com.kumela.cmeter.ui.adapters.search;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.list.SearchSuggestionItem;

/**
 * Created by Toko on 01,August,2020
 **/

public class SearchSuggestionAdapter extends
        ListAdapter<SearchSuggestionItem, SearchSuggestionAdapter.SearchSuggestionItemViewHolder> implements SearchSuggestionItemViewMvc.Listener {

    public interface Listener {
        void onSearchSuggestionItemClicked(@NonNull SearchSuggestionItem searchSuggestionItem);

        void onSearchSuggestionItemCommitClick(@NonNull String suggestionName);
    }

    private final Listener mListener;
    private final ViewMvcFactory mViewMvcFactory;

    public SearchSuggestionAdapter(Listener listener, ViewMvcFactory viewMvcFactory) {
        super(DIFF);

        this.mListener = listener;
        this.mViewMvcFactory = viewMvcFactory;
    }

    private static final DiffUtil.ItemCallback<SearchSuggestionItem> DIFF = new DiffUtil.ItemCallback<SearchSuggestionItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull SearchSuggestionItem oldItem, @NonNull SearchSuggestionItem newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchSuggestionItem oldItem, @NonNull SearchSuggestionItem newItem) {
            return oldItem.getSuggestionName().equals(newItem.getSuggestionName()) &&
                    oldItem.getDrawableId() == newItem.getDrawableId();
        }
    };

    @NonNull
    @Override
    public SearchSuggestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchSuggestionItemViewMvc viewMvc = mViewMvcFactory.newInstance(SearchSuggestionItemViewMvc.class, parent);
        viewMvc.registerListener(this);
        return new SearchSuggestionItemViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestionItemViewHolder holder, int position) {
        holder.mItemViewMvc.bindSearchSuggestionItem(getItem(position));
    }

    @Override
    public void onSearchSuggestionItemClicked(@NonNull SearchSuggestionItem searchSuggestionItem) {
        mListener.onSearchSuggestionItemClicked(searchSuggestionItem);
    }

    @Override
    public void onSearchSuggestionCommitClick(@NonNull String suggestionName) {
        mListener.onSearchSuggestionItemCommitClick(suggestionName);
    }

    static final class SearchSuggestionItemViewHolder extends RecyclerView.ViewHolder {
        private final SearchSuggestionItemViewMvc mItemViewMvc;

        public SearchSuggestionItemViewHolder(@NonNull SearchSuggestionItemViewMvc viewMvc) {
            super(viewMvc.getRootView());

            this.mItemViewMvc = viewMvc;
        }
    }

}
