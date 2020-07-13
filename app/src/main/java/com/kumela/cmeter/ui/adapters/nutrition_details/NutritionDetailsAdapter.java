package com.kumela.cmeter.ui.adapters.nutrition_details;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.list.NutritionDetailItem;

import java.util.List;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsAdapter extends RecyclerView.Adapter<NutritionDetailsAdapter.NutritionDetailsViewHolder> {

    private ViewMvcFactory mViewMvcFactory;
    private List<NutritionDetailItem> mData;

    public NutritionDetailsAdapter(ViewMvcFactory viewMvcFactory, List<NutritionDetailItem> data) {
        this.mViewMvcFactory = viewMvcFactory;
        this.mData = data;
    }

    @NonNull
    @Override
    public NutritionDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NutritionDetailsItemMvc viewMvc = mViewMvcFactory.newInstance(NutritionDetailsItemMvc.class, parent);
        return new NutritionDetailsViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionDetailsViewHolder holder, int position) {
        holder.mNutritionDetailsItemMvc.bindDetailsItem(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static final class NutritionDetailsViewHolder extends RecyclerView.ViewHolder {
        private final NutritionDetailsItemMvc mNutritionDetailsItemMvc;

        private NutritionDetailsViewHolder(NutritionDetailsItemMvc nutritionDetailsItemMvc) {
            super(nutritionDetailsItemMvc.getRootView());
            this.mNutritionDetailsItemMvc = nutritionDetailsItemMvc;
        }
    }
}
