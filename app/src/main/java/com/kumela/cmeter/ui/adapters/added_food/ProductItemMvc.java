package com.kumela.cmeter.ui.adapters.added_food;

import com.kumela.cmeter.model.local.ProductModel;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewMvc;

/**
 * Created by Toko on 21,July,2020
 **/

public interface ProductItemMvc extends ObservableViewMvc<ProductItemMvc.Listener> {
    interface Listener {
        void onProductClick(String foodName);
    }

    void bindAddedFood(ProductModel addedFood);
}
