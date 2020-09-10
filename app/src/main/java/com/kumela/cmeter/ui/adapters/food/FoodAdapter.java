package com.kumela.cmeter.ui.adapters.food;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.list.FoodListModel;

/**
 * Created by Toko on 23,June,2020
 **/

public class FoodAdapter extends ListAdapter<FoodListModel, FoodAdapter.FoodItemViewHolder>
        implements FoodListItemMvc.Listener {

    public interface Listener {
        void onSearchItemClicked(FoodListModel foodListModel);
    }

    private final Listener mListener;
    private final ViewMvcFactory mViewMvcFactory;

    public FoodAdapter(Listener listener, ViewMvcFactory viewMvcFactory) {
        super(DIFF_CALLBACK);

        this.mListener = listener;
        this.mViewMvcFactory = viewMvcFactory;
    }

    private static final DiffUtil.ItemCallback<FoodListModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<FoodListModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull FoodListModel oldItem, @NonNull FoodListModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull FoodListModel oldItem, @NonNull FoodListModel newItem) {
            return oldItem.label.equals(newItem.label) &&
                    oldItem.calories == newItem.calories;
        }
    };

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodListItemMvc viewMvc = mViewMvcFactory.newInstance(FoodListItemMvc.class, parent);
        viewMvc.registerListener(this);
        return new FoodItemViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        holder.mFoodListItemMvc.bindSearchItem(getItem(position));
    }

    @Override
    public void onSearchItemClicked(FoodListModel foodListModel) {
        mListener.onSearchItemClicked(foodListModel);
    }

    static final class FoodItemViewHolder extends RecyclerView.ViewHolder {
        private final FoodListItemMvc mFoodListItemMvc;

        private FoodItemViewHolder(FoodListItemMvc foodListItemMvc) {
            super(foodListItemMvc.getRootView());
            this.mFoodListItemMvc = foodListItemMvc;
        }
    }
}
