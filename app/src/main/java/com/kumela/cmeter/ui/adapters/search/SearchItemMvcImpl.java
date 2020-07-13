package com.kumela.cmeter.ui.adapters.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

import java.util.Locale;

/**
 * Created by Toko on 02,July,2020
 **/

public class SearchItemMvcImpl extends BaseObservableViewMvc<SearchItemMvc.Listener>
        implements SearchItemMvc {

    private final TextView tvName;
    private final TextView tvDesc;
    private final AppCompatImageButton btnAdd;

    private SearchItem mSearchItem;

    public SearchItemMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_search, parent, false));

        this.tvName = findViewById(R.id.tv_item_search_food_name);
        this.tvDesc = findViewById(R.id.tv_item_search_food_desc);
        this.btnAdd = findViewById(R.id.btn_item_search_add);

        getRootView().setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onSearchItemClicked(mSearchItem);
            }
        });
    }

    @Override
    public void bindSearchItem(SearchItem searchItem) {
        mSearchItem = searchItem;

        String foodDesc = String.format(
                Locale.getDefault(),
                getContext().getString(R.string.value_unit),
                Utils.format(searchItem.servingQuantity), searchItem.servingUnit
        );

        tvName.setText(searchItem.foodName);
        tvDesc.setText(foodDesc);
        btnAdd.setOnClickListener(v -> {
            // TODO: 7/2/2020 implement onClick to write into database
        });
    }
}
