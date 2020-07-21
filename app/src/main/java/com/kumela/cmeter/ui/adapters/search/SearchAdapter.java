package com.kumela.cmeter.ui.adapters.search;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.api.search.SearchItem;

/**
 * Created by Toko on 23,June,2020
 **/

public class SearchAdapter extends ListAdapter<SearchItem, SearchAdapter.SearchItemViewHolder>
        implements SearchItemMvc.Listener {

    public interface Listener {
        void onSearchItemClicked(SearchItem searchItem);

        void onAddButtonClicked(SearchItem searchItem);
    }

    private final Listener mListener;
    private final ViewMvcFactory mViewMvcFactory;

    public SearchAdapter(Listener listener, ViewMvcFactory viewMvcFactory) {
        super(DIFF_CALLBACK);

        this.mListener = listener;
        this.mViewMvcFactory = viewMvcFactory;
    }

    private static final DiffUtil.ItemCallback<SearchItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<SearchItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull SearchItem oldItem, @NonNull SearchItem newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchItem oldItem, @NonNull SearchItem newItem) {
            return oldItem.foodName.equals(newItem.foodName) &&
                    oldItem.servingUnit.equals(newItem.servingUnit) &&
                    oldItem.servingQuantity == newItem.servingQuantity;
        }
    };

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.e(getClass().getSimpleName(), "onDetachedFromRecyclerView: called");
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItemMvc viewMvc = mViewMvcFactory.newInstance(SearchItemMvc.class, parent);
        viewMvc.registerListener(this);
        return new SearchItemViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        holder.mSearchItemMvc.bindSearchItem(getItem(position));
    }

    static final class SearchItemViewHolder extends RecyclerView.ViewHolder {
        private final SearchItemMvc mSearchItemMvc;

        private SearchItemViewHolder(SearchItemMvc searchItemMvc) {
            super(searchItemMvc.getRootView());
            this.mSearchItemMvc = searchItemMvc;
        }
    }


    @Override
    public void onSearchItemClicked(SearchItem searchItem) {
        mListener.onSearchItemClicked(searchItem);
    }

    @Override
    public void onAddButtonClicked(SearchItem searchItem) {
        mListener.onAddButtonClicked(searchItem);
    }
}
