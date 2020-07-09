package com.kumela.cmeter.ui.common;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Utils;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.internal.Util;

/**
 * Created by Toko on 06,July,2020
 **/

public class BaseNutritionPieChart extends PieChart {

    public BaseNutritionPieChart(Context context) {
        super(context);
    }

    public BaseNutritionPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseNutritionPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        setUsePercentValues(true);
        getDescription().setEnabled(false);
        setTouchEnabled(false);

        setDrawHoleEnabled(true);
        setHoleColor(Color.TRANSPARENT);

        getLegend().setEnabled(false);
    }

    private static final String TAG = "BaseNutritionPieChart";
    public void setHoleRadius(int height) {
        super.setHoleRadius(Utils.pxToDp(getContext(), height) * .5f);
    }

    public void setCenterText(@NonNull String text) {
        setDrawCenterText(true);
        setCenterTextColor(getResources().getColor(R.color.colorWhite));
        setCenterTextSize(Utils.pxToDp(getContext(), getHeight() * .15f));

        super.setCenterText(String.format(
                getResources().getString(R.string.value_unit),
                text,
                getResources().getString(R.string.kcal)
        ));
    }

    /**
     * set data for base nutrition pie
     *
     * @param data hash map of string (float value) and int (color)
     */
    public void setPieData(@NonNull Map<String, Integer> data) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            entries.add(new PieEntry(Float.parseFloat(entry.getKey())));
            colors.add(entry.getValue());
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(0f);

        PieData pieDataSet = new PieData(dataSet);
        pieDataSet.setDrawValues(false);
        setData(pieDataSet);
        invalidate();
    }

    public void animateY() {
        super.animateY(1000, Easing.EaseInCubic);
    }
}
