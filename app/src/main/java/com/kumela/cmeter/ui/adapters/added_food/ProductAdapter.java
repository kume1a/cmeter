package com.kumela.cmeter.ui.adapters.added_food;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.ProductModel;

import java.util.List;

/**
 * Created by Toko on 21,July,2020
 **/

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.AddFoodsViewHolder> {

    private ViewMvcFactory mViewMvcFactory;
    private List<ProductModel> mData;

    public ProductAdapter(List<ProductModel> data, ViewMvcFactory viewMvcFactory) {
        this.mViewMvcFactory = viewMvcFactory;
        this.mData = data;
    }

    @NonNull
    @Override
    public AddFoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemMvc viewMvc = mViewMvcFactory.newInstance(ProductItemMvc.class, parent);
        return new AddFoodsViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFoodsViewHolder holder, int position) {
        holder.mProductItemMvc.bindAddedFood(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class AddFoodsViewHolder extends RecyclerView.ViewHolder {
        private final ProductItemMvc mProductItemMvc;

        private AddFoodsViewHolder(ProductItemMvc productItemMvc) {
            super(productItemMvc.getRootView());
            this.mProductItemMvc = productItemMvc;
        }
    }
}
