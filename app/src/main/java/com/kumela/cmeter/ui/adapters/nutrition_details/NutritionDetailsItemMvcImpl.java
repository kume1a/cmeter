package com.kumela.cmeter.ui.adapters.nutrition_details;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kumela.cmeter.R;
import com.kumela.cmeter.model.local.list.NutritionDetailListModel;
import com.kumela.cmeter.ui.common.mvc.BaseViewMvc;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsItemMvcImpl extends BaseViewMvc implements NutritionDetailsItemMvc {

    private final TextView tvName;
    private final TextView tvValue;
    private final View topMargin20;

    public NutritionDetailsItemMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.item_nutrition, parent, false));
        this.tvName = findViewById(R.id.tv_item_nutrition_name);
        this.tvValue = findViewById(R.id.tv_item_nutrition_value);
        this.topMargin20 = findViewById(R.id.item_nutrition_top_margin);
    }

    @Override
    public void bindDetailsItem(NutritionDetailListModel item) {
        if (item.isHeader()) {
            topMargin20.setVisibility(View.VISIBLE);

            tvName.setTextSize(18);
            tvValue.setTextSize(18);

            tvName.setTypeface(null, Typeface.BOLD);
            tvValue.setTypeface(null, Typeface.BOLD);
        }

        Float itemValue = item.getValue();
        if (itemValue != null) {
            String value;
            if (itemValue == 0) value = "0";
            else if (itemValue < .1) value = "< 0.1";
            else value = String.format(getResources().getString(R.string.value_1f), itemValue);

            tvValue.setText(String.format(
                    getResources().getString(R.string.value_unit),
                    value, item.getUnit()
            ));
        }
        tvName.setText(item.getName());
    }
}
