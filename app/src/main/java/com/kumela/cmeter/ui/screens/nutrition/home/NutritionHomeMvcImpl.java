package com.kumela.cmeter.ui.screens.nutrition.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvcImpl;

import java.util.ArrayList;

/**
 * Created by Toko on 26,June,2020
 **/

public class NutritionHomeMvcImpl extends BaseObservableViewMvcImpl<NutritionHomeMvc.Listener>
        implements NutritionHomeMvc {

    private FloatingActionButton mFabMain, mFabBreakfast, mFabDinner, mFabSupper, mFabSnacks;
    private CardView mCvBreakfast, mCvDinner, mCvSupper, mCvSnacks;
    private View mViewDim;

    private OvershootInterpolator interpolator = new OvershootInterpolator();
    private boolean isMenuOpen = false;

    private PieChart mPieChart;

    public NutritionHomeMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.nutrition_home_activity, container, false));

        mViewDim = findViewById(R.id.view_dim_nutrition_home);

        mFabMain = findViewById(R.id.fab_main);
        mFabBreakfast = findViewById(R.id.fab_breakfast);
        mFabDinner = findViewById(R.id.fab_dinner);
        mFabSupper = findViewById(R.id.fab_supper);
        mFabSnacks = findViewById(R.id.fab_snacks);

        mCvBreakfast = findViewById(R.id.cv_home_breakfast);
        mCvDinner = findViewById(R.id.cv_home_dinner);
        mCvSupper = findViewById(R.id.cv_home_supper);
        mCvSnacks = findViewById(R.id.cv_home_snacks);

        fabMenuText(mCvBreakfast, getResources().getString(R.string.breakfast));
        fabMenuText(mCvDinner, getResources().getString(R.string.dinner));
        fabMenuText(mCvSupper, getResources().getString(R.string.supper));
        fabMenuText(mCvSnacks, getResources().getString(R.string.snacks));


        mViewDim.setOnClickListener(v -> closeMenu());

        mFabMain.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onFabClick(isMenuOpen);
        });
        mFabBreakfast.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(getResources().getString(R.string.breakfast));
        });
        mFabDinner.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(getResources().getString(R.string.dinner));
        });
        mFabSupper.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(getResources().getString(R.string.supper));
        });
        mFabSnacks.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(getResources().getString(R.string.snacks));
        });

        setupCalorieDashboardPie();
    }

    private void fabMenuText(@NonNull CardView cardView, @NonNull String text) {
        ((TextView) cardView.findViewById(R.id.tv_item_menu_fab_text)).setText(text);
    }

    @Override
    public void openMenu() {
        isMenuOpen = !isMenuOpen;

        mViewDim.setVisibility(View.VISIBLE);
        mFabMain.animate().setInterpolator(interpolator).rotation(225f).setDuration(300).start();

        animateMenu(mFabBreakfast);
        animateMenu(mFabDinner);
        animateMenu(mFabSupper);
        animateMenu(mFabSnacks);

        animateMenu(mCvBreakfast);
        animateMenu(mCvDinner);
        animateMenu(mCvSupper);
        animateMenu(mCvSnacks);
    }

    @Override
    public void closeMenu() {
        isMenuOpen = !isMenuOpen;

        mViewDim.setVisibility(View.GONE);
        mFabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        animateMenu(mFabBreakfast);
        animateMenu(mFabDinner);
        animateMenu(mFabSupper);
        animateMenu(mFabSnacks);

        animateMenu(mCvBreakfast);
        animateMenu(mCvDinner);
        animateMenu(mCvSupper);
        animateMenu(mCvSnacks);
    }

    private void animateMenu(View v) {
        float translationY = isMenuOpen ? 0f : 100f;
        float alpha = isMenuOpen ? 1f : 0f;

        v.animate()
                .translationY(translationY)
                .alpha(alpha)
                .setInterpolator(interpolator)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!isMenuOpen) v.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (isMenuOpen) v.setVisibility(View.VISIBLE);
                        super.onAnimationStart(animation);
                    }
                }).start();
    }

    private void setupCalorieDashboardPie() {
        mPieChart = findViewById(R.id.pie_home_dashboard_calories);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.TRANSPARENT);
        mPieChart.setHoleRadius(getResources().getDimension(R.dimen.pie_home_dashboard_calorie) * .35f);
        mPieChart.animateY(1400, Easing.EaseInCubic);

        mPieChart.setDrawCenterText(true);
        mPieChart.setCenterText("50%");
        mPieChart.setCenterTextColor(getResources().getColor(R.color.colorWhite));
        mPieChart.setCenterTextSize(18);

        setData();
        mPieChart.getLegend().setEnabled(false);
    }

    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(1000f));
        entries.add(new PieEntry(100f));

        PieDataSet dataSet = new PieDataSet(entries, "label from data set");

        dataSet.setSliceSpace(2f);

        ArrayList<Integer> colors = new ArrayList<>();

        int colorAccent = getResources().getColor(R.color.colorAccent);
        colors.add(colorAccent);
        colors.add(ColorUtils.setAlphaComponent(colorAccent, 50));

        dataSet.setColors(colors);
        dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        mPieChart.setData(data);
        mPieChart.invalidate();
    }
}
