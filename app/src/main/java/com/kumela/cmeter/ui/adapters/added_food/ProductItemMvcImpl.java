package com.kumela.cmeter.ui.adapters.added_food;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.local.list.ProductHistoryListModel;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 21,July,2020
 **/

public class ProductItemMvcImpl extends BaseObservableViewMvc<ProductItemMvc.Listener>
        implements ProductItemMvc {

    private final MaterialTextView tvName;
    private final MaterialTextView tvServingAmount;
    private final MaterialTextView tvCalories;
    private final MaterialTextView tvDate;
    private final AppCompatImageView ivFavorite;

    private ProductHistoryListModel mProduct;

    public ProductItemMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_product, parent, false));

        this.tvName = findViewById(R.id.tv_item_product_name);
        this.tvServingAmount = findViewById(R.id.tv_item_product_food_serving_unit);
        this.tvCalories = findViewById(R.id.tv_item_product_calories);
        this.tvDate = findViewById(R.id.tv_item_product_date);
        this.ivFavorite = findViewById(R.id.iv_item_product_favorite);

        getRootView().setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onProductClick(mProduct);
            }
        });
    }

    @Override
    public void bindHistoryProduct(ProductHistoryListModel item,
                                   boolean showDate,
                                   boolean firstItem,
                                   boolean showFavorite) {
        mProduct = item;

        String servingAmount = getResources().getString(R.string.value_unit, Utils.format(item.getQuantity()), item.getMeasureUnit());

        tvName.setText(item.getFoodName());
        tvServingAmount.setText(servingAmount);
        tvCalories.setText(getResources().getString(R.string.value_cal, (int) item.getCalories()));

        ivFavorite.setVisibility(showFavorite ? View.VISIBLE : View.GONE);

        if (item.getFoodName().equalsIgnoreCase("Fish")) {
            String TAG = getClass().getSimpleName();
            Log.d(TAG, "bindHistoryProduct() called with: item = [" + item + "], fistItem = [" + firstItem + "], showDate = [" + showDate + "], showFavorite = [" + showFavorite + "]");
        }
        if (showDate) {
            tvDate.setText(item.getDate());
            tvDate.setVisibility(View.VISIBLE);
        }

        if (!firstItem && showDate) {
            tvDate.setPadding(0, 48, 0, 16);
        }
    }
}
