package com.kumela.cmeter.ui.adapters.nutrition_details;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.NutritionDetailItem;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsAdapter
        extends ListAdapter<NutritionDetailItem, NutritionDetailsAdapter.NutritionDetailsViewHolder> {

    private ViewMvcFactory mViewMvcFactory;

    private static final DiffUtil.ItemCallback<NutritionDetailItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<NutritionDetailItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull NutritionDetailItem oldItem, @NonNull NutritionDetailItem newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NutritionDetailItem oldItem, @NonNull NutritionDetailItem newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getValue().equals(newItem.getValue());
        }
    };

    public NutritionDetailsAdapter(ViewMvcFactory viewMvcFactory) {
        super(DIFF_CALLBACK);
        this.mViewMvcFactory = viewMvcFactory;
    }

    @NonNull
    @Override
    public NutritionDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NutritionDetailsItemMvc viewMvc = mViewMvcFactory.newInstance(NutritionDetailsItemMvc.class, parent);
        return new NutritionDetailsViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionDetailsViewHolder holder, int position) {
        holder.mNutritionDetailsItemMvc.bindDetailsItem(getItem(position));
    }

    static final class NutritionDetailsViewHolder extends RecyclerView.ViewHolder {
        private final NutritionDetailsItemMvc mNutritionDetailsItemMvc;

        private NutritionDetailsViewHolder(NutritionDetailsItemMvc nutritionDetailsItemMvc) {
            super(nutritionDetailsItemMvc.getRootView());
            this.mNutritionDetailsItemMvc = nutritionDetailsItemMvc;
        }
    }
}
