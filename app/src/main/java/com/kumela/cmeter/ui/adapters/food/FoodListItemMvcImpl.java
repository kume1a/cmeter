package com.kumela.cmeter.ui.adapters.food;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kumela.cmeter.R;
import com.kumela.cmeter.model.local.list.FoodListModel;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

/**
 * Created by Toko on 02,July,2020
 **/

public class FoodListItemMvcImpl extends BaseObservableViewMvc<FoodListItemMvc.Listener>
        implements FoodListItemMvc {

    private final TextView tvName;
    private final TextView tvCal;

    private FoodListModel mFoodListModel;

    public FoodListItemMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_food, parent, false));

        this.tvName = findViewById(R.id.tv_item_food_name);
        this.tvCal = findViewById(R.id.tv_item_food_cal);

        getRootView().setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onSearchItemClicked(mFoodListModel);
            }
        });
    }

    @Override
    public void bindSearchItem(FoodListModel foodListModel) {
        mFoodListModel = foodListModel;

        tvName.setText(foodListModel.label);
        tvCal.setText(getResources().getString(R.string.value_cal, (int) foodListModel.calories));
    }
}
