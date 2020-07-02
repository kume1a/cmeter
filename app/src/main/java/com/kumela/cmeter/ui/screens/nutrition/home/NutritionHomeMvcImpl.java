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
import androidx.annotation.Nullable;
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

public class NutritionHomeMvcImpl extends BaseObservableViewMvcImpl<NutritionHomeMvcImpl.Listener> {

    interface Listener {
        void onNavigateToAddFood(boolean isMenuOpen, @Nullable String title);
    }

    private FloatingActionButton mFabMain, mFabBreakfast, mFabDinner, mFabSupper, mFabSnacks;
    private CardView mCvBreakfast, mCvDinner, mCvSupper, mCvSnacks;

    private OvershootInterpolator interpolator = new OvershootInterpolator();
    private boolean isMenuOpen = false;

    private PieChart mPieChart;

    private AnimationController mAnimationController;
    private ChartController mChartController;

    public NutritionHomeMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.nutrition_home_activity, container, false));

        mAnimationController = new AnimationController();
        mChartController = new ChartController();
    }

    public void closeMenu() {
        mAnimationController.closeMenu();
    }

    public void openMenu() {
        mAnimationController.openMenu();
    }

    private void initFabMenu() {
        fabMenuText(mCvBreakfast, getContext().getString(R.string.fab_breakfast));
        fabMenuText(mCvDinner, getContext().getString(R.string.fab_dinner));
        fabMenuText(mCvSupper, getContext().getString(R.string.fab_supper));
        fabMenuText(mCvSnacks, getContext().getString(R.string.fab_snacks));
    }

    private void fabMenuText(@NonNull CardView cardView, @NonNull String text) {
        ((TextView) cardView.findViewById(R.id.tv_item_menu_fab_text)).setText(text);
    }

    void init() {
        mChartController.setupCalorieDashboardPie();

        View.OnClickListener menuListener = v -> {
            int id = v.getId();
            String title = null;
            if (id != R.id.fab_main) {
                switch (id) {
                    case R.id.fab_breakfast:
                        title = "Breakfast";
                        break;
                    case R.id.fab_dinner:
                        title = "Dinner";
                        break;
                    case R.id.fab_supper:
                        title = "Supper";
                        break;
                    case R.id.fab_snacks:
                        title = "Snacks";
                        break;
                }
            }
            for (Listener listener : getListeners()) {
                listener.onNavigateToAddFood(isMenuOpen, title);
            }
        };

        mFabMain = findViewById(R.id.fab_main);
        mFabBreakfast = findViewById(R.id.fab_breakfast);
        mFabDinner = findViewById(R.id.fab_dinner);
        mFabSupper = findViewById(R.id.fab_supper);
        mFabSnacks = findViewById(R.id.fab_snacks);

        mCvBreakfast = findViewById(R.id.cv_home_breakfast);
        mCvDinner = findViewById(R.id.cv_home_dinner);
        mCvSupper = findViewById(R.id.cv_home_supper);
        mCvSnacks = findViewById(R.id.cv_home_snacks);

        mFabMain.setOnClickListener(menuListener);
        mFabBreakfast.setOnClickListener(menuListener);
        mFabDinner.setOnClickListener(menuListener);
        mFabSupper.setOnClickListener(menuListener);
        mFabSnacks.setOnClickListener(menuListener);

        initFabMenu();
    }

    private class AnimationController {

        private void openMenu() {
            isMenuOpen = !isMenuOpen;

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

        private void closeMenu() {
            isMenuOpen = !isMenuOpen;

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
    }

    private class ChartController {
        private void setupCalorieDashboardPie() {
            mPieChart = findViewById(R.id.pie_home_dashboard_calories);
            mPieChart.setUsePercentValues(true);
            mPieChart.getDescription().setEnabled(false);

            mPieChart.setDrawHoleEnabled(true);
            mPieChart.setHoleColor(Color.TRANSPARENT);
            mPieChart.setHoleRadius(getResources().getDimension(R.dimen.home_dashboard_calorie_pie) * .35f);
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

            PieDataSet dataSet = new PieDataSet(entries, "label from dataset");

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
//        chart.highlightValues(null);
            mPieChart.invalidate();
        }
    }
}
