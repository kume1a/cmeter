package com.kumela.cmeter.ui.adapters.nutrition_details;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kumela.cmeter.R;
import com.kumela.cmeter.model.local.NutritionDetailItem;
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

    private void setHeaderAppearance(TextView textView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(getContext(), R.style.Header_Medium);
        } else {
            textView.setTextAppearance(R.style.Header_Medium);
        }
    }

    @Override
    public void bindDetailsItem(NutritionDetailItem item) {
        if (item.isHeader()) {

            if (!item.getName().equals("Energy")) topMargin20.setVisibility(View.VISIBLE);

            setHeaderAppearance(tvName);
            setHeaderAppearance(tvValue);
        }

        if (item.hasValue() && item.getValue() != null) {
            Float itemValue = item.getValue();
            String value;
            if (itemValue == 0) value = "0";
            else if (itemValue < .1) value = "> 0.1";
            else value = String.format(getResources().getString(R.string.value_1f), itemValue);

            tvValue.setText(String.format(
                    getResources().getString(R.string.value_unit),
                    value, item.getUnit()
            ));
        }
        tvName.setText(item.getName());
    }
}
