package com.kumela.cmeter.ui.common.util;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.kumela.cmeter.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Toko on 06,July,2020
 **/

public class NutritionPieChart extends PieChart {

    public NutritionPieChart(Context context) {
        super(context);
    }

    public NutritionPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NutritionPieChart(Context context, AttributeSet attrs, int defStyle) {
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

    public void setHoleRadius(int height, float fraction) {
        super.setHoleRadius(height * fraction);
    }

    public void setCenterText(@NonNull String text, @IntRange(from = 12, to = 30) int textSize) {
        setDrawCenterText(true);
        setCenterTextColor(getResources().getColor(R.color.colorWhite));
        setCenterTextSize(textSize);

        super.setCenterText(text);
    }

    /**
     * set data for base nutrition pie
     *
     * @param data hash map of string (float value) and int (color)
     */
    public void setPieData(@NonNull Map<Float, Integer> data) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        for (Map.Entry<Float, Integer> entry : data.entrySet()) {
            entries.add(new PieEntry(entry.getKey()));
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
