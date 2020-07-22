package com.kumela.cmeter.ui.adapters.added_food;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.local.ProductModel;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 21,July,2020
 **/

public class ProductItemMvcImpl extends BaseObservableViewMvc<ProductItemMvc.Listener>
        implements ProductItemMvc {

    private final MaterialTextView tvName;
    private final MaterialTextView tvServingAmount;
    private final MaterialTextView tvCalories;

    private ProductModel mProduct;

    public ProductItemMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_product, parent, false));

        this.tvName = findViewById(R.id.tv_item_product_name);
        this.tvServingAmount = findViewById(R.id.tv_item_product_food_serving_unit);
        this.tvCalories = findViewById(R.id.tv_item_product_calories);

        getRootView().setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onProductClick(mProduct.foodName);
            }
        });
    }

    @Override
    public void bindAddedFood(ProductModel product) {
        mProduct = product;

        String servingAmount = getResources().getString(R.string.value_unit, Utils.format(product.quantity), product.servingUnit);

        tvName.setText(product.foodName);
        tvServingAmount.setText(servingAmount);
        tvCalories.setText(getResources().getString(R.string.value_cal, (int) product.calories));
    }
}
