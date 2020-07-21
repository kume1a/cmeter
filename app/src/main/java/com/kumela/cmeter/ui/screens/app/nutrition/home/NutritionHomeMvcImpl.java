package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;

import com.github.mikephil.charting.animation.Easing;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.local.NutritionHomeModel;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;
import com.kumela.cmeter.ui.common.util.NutritionPieChart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Toko on 26,June,2020
 **/

public class NutritionHomeMvcImpl extends BaseObservableViewMvc<NutritionHomeMvc.Listener>
        implements NutritionHomeMvc {

    private FloatingActionButton mFabMain, mFabBreakfast, mFabDinner, mFabSupper, mFabSnacks;
    private CardView mCvBreakfast, mCvDinner, mCvSupper, mCvSnacks;
    private View mViewDim;

    private OvershootInterpolator mOvershootInterpolator = new OvershootInterpolator();
    private boolean isMenuOpen = false;

    private MaterialTextView mTvCurrentCalories;
    private MaterialTextView mTvGoalCalories;

    private MaterialTextView mTvCarbohydratesProgress;
    private MaterialTextView mTvFatsProgress;
    private MaterialTextView mTvProteinProgress;

    private ProgressBar mPbCarbohydrates;
    private ProgressBar mPbFats;
    private ProgressBar mPbProteins;

    private MaterialTextView mTvWeightGoal;
    private MaterialTextView mTvCurrentWeight;
    private AppCompatImageButton mBtnAddWeight;
    private AppCompatImageButton mBtnSubtractWeight; // TODO: 7/15/2020 implement weight change

    private CardView mCardBreakfast;
    private CardView mCardDinner;
    private CardView mCardSupper;
    private CardView mCardSnacks;

    public NutritionHomeMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.nutrition_home_fragment, container, false));

        mTvCurrentCalories = findViewById(R.id.tv_nutrition_home_dashboard_current_calories);
        mTvGoalCalories = findViewById(R.id.tv_nutrition_home_dashboard_goal_calories);

        mTvCarbohydratesProgress = findViewById(R.id.tv_nutrition_home_dashboard_progress_carbs);
        mTvFatsProgress = findViewById(R.id.tv_nutrition_home_dashboard_progress_fats);
        mTvProteinProgress = findViewById(R.id.tv_nutrition_home_dashboard_progress_protein);

        mPbCarbohydrates = findViewById(R.id.pb_nutrition_home_dashboard_carbs);
        mPbFats = findViewById(R.id.pb_nutrition_home_dashboard_fats);
        mPbProteins = findViewById(R.id.pb_nutrition_home_dashboard_protein);

        mTvWeightGoal = findViewById(R.id.tv_nutrition_home_weight_goal);
        mTvCurrentWeight = findViewById(R.id.tv_nutrition_home_weight_current_weight);
        mBtnAddWeight = findViewById(R.id.btn_nutrition_home_weight_add);
        mBtnSubtractWeight = findViewById(R.id.btn_nutrition_home_weight_minus);

        mCardBreakfast = findViewById(R.id.cv_nutrition_home_breakfast);
        mCardDinner = findViewById(R.id.cv_nutrition_home_dinner);
        mCardSupper = findViewById(R.id.cv_nutrition_home_supper);
        mCardSnacks = findViewById(R.id.cv_nutrition_home_snacks);

        mCardBreakfast.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onMealClick(Constants.BREAKFAST);
        });
        mCardDinner.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onMealClick(Constants.DINNER);
        });
        mCardSupper.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onMealClick(Constants.SUPPER);
        });
        mCardSnacks.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onMealClick(Constants.SNACKS);
        });
    }

    @Override
    public void showFabMenu(View fabMenu, View dim) {
        fabMenu.setVisibility(View.VISIBLE);

        mViewDim = dim;
        mViewDim.setOnClickListener(v -> closeMenu());

        mFabMain = fabMenu.findViewById(R.id.fab_main);
        mFabBreakfast = fabMenu.findViewById(R.id.fab_breakfast);
        mFabDinner = fabMenu.findViewById(R.id.fab_dinner);
        mFabSupper = fabMenu.findViewById(R.id.fab_supper);
        mFabSnacks = fabMenu.findViewById(R.id.fab_snacks);

        mCvBreakfast = fabMenu.findViewById(R.id.cv_home_breakfast);
        mCvDinner = fabMenu.findViewById(R.id.cv_home_dinner);
        mCvSupper = fabMenu.findViewById(R.id.cv_home_supper);
        mCvSnacks = fabMenu.findViewById(R.id.cv_home_snacks);

        fabMenuText(mCvBreakfast, getResources().getString(R.string.breakfast));
        fabMenuText(mCvDinner, getResources().getString(R.string.dinner));
        fabMenuText(mCvSupper, getResources().getString(R.string.supper));
        fabMenuText(mCvSnacks, getResources().getString(R.string.snacks));

        mFabMain.show();

        mFabMain.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onFabClick(isMenuOpen);
        });
        mFabBreakfast.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(Constants.BREAKFAST);
        });
        mFabDinner.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(Constants.DINNER);
        });
        mFabSupper.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(Constants.SUPPER);
        });
        mFabSnacks.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(Constants.SNACKS);
        });
    }

    @Override
    public void hideFabMenu(View fabMenu) {
        mFabMain.hide(new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onHidden(FloatingActionButton fab) {
                super.onHidden(fab);
                fabMenu.setVisibility(View.GONE);
            }
        });
        mViewDim.setOnClickListener(null);
    }

    private void fabMenuText(@NonNull CardView cardView, @NonNull String text) {
        ((TextView) cardView.findViewById(R.id.tv_item_menu_fab_text)).setText(text);
    }

    @Override
    public void openMenu() {
        isMenuOpen = !isMenuOpen;

        mViewDim.setVisibility(View.VISIBLE);
        mFabMain.animate().setInterpolator(mOvershootInterpolator).rotation(225f).setDuration(300).start();

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
        mFabMain.animate().setInterpolator(mOvershootInterpolator).rotation(0f).setDuration(300).start();

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
                .setInterpolator(mOvershootInterpolator)
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

    private void setupCalorieDashboardPie(int goalCalories, int currentCalories) {
        final int calorieProgress = (int) ((float) currentCalories / goalCalories * 100);
        Map<Float, Integer> data = new HashMap<>();

        int colorAccent = getResources().getColor(R.color.colorAccent);
        data.put((float) currentCalories, colorAccent);
        if (goalCalories > calorieProgress) {
            data.put((float) (goalCalories - currentCalories), ColorUtils.setAlphaComponent(colorAccent, 50));
        }

        NutritionPieChart pieChart = findViewById(R.id.pie_nutrition_home_dashboard_calories);

        pieChart.setHoleRadius((int) (getResources().getDimension(R.dimen.pie_home_dashboard_calorie)), .35f);
        pieChart.setCenterText(getResources().getString(R.string.value_percent, calorieProgress), 18);
        pieChart.setPieData(data);
        pieChart.animateY(1500, Easing.EaseInCubic);
    }

    @Override
    public void bindHomeModelInfo(NutritionHomeModel info) {
        Log.d(getClass().getSimpleName(), "bindHomeModelInfo: called, info = " + info);

        // dashboard
        mTvCurrentCalories.setText(String.valueOf(info.currentCalories));
        setupCalorieDashboardPie(info.goalCalories, info.currentCalories);

        mTvGoalCalories.setText(getResources().getString(R.string.nutrition_dashboard_goal_calories, info.goalCalories));

        mTvCarbohydratesProgress.setText(getResources().getString(R.string.value_slash_value_g, info.currentCarbohydrates, info.goalCarbohydrates));
        mTvFatsProgress.setText(getResources().getString(R.string.value_slash_value_g, info.currentFats, info.goalFats));
        mTvProteinProgress.setText(getResources().getString(R.string.value_slash_value_g, info.currentProteins, info.goalProteins));

        mPbCarbohydrates.setMax(info.goalCarbohydrates);
        mPbFats.setMax(info.goalFats);
        mPbProteins.setMax(info.goalProteins);

        animateProgressBar(mPbCarbohydrates, info.currentCarbohydrates);
        animateProgressBar(mPbFats, info.currentFats);
        animateProgressBar(mPbProteins, info.currentProteins);

        // weight
        mTvWeightGoal.setText(getResources().getString(R.string.nutrition_dashboard_goal_weight, info.goalWeight));
        mTvCurrentWeight.setText(String.valueOf(info.currentWeight));

        // meal cards
        bindMealCardDetails(mCardBreakfast, R.string.breakfast, info.breakfastProgress, info.currentBreakfastMacros);
        bindMealCardDetails(mCardDinner, R.string.dinner, info.dinnerProgress, info.currentDinnerMacros);
        bindMealCardDetails(mCardSupper, R.string.supper, info.supperProgress, info.currentSupperMacros);
        bindMealCardDetails(mCardSnacks, R.string.snacks, info.snacksProgress, info.currentSnacksMacros);
    }

    private void bindMealCardDetails(CardView cardView, @StringRes int titleResId, int calories, int[] macros) {
        MaterialTextView tvHeader = cardView.findViewById(R.id.tv_meal_card_header);
        MaterialTextView tvCalories = cardView.findViewById(R.id.tv_meal_card_calories);
        MaterialTextView tvCarbohydrates = cardView.findViewById(R.id.tv_meal_card_carbs);
        MaterialTextView tvFats = cardView.findViewById(R.id.tv_meal_card_fats);
        MaterialTextView tvProteins = cardView.findViewById(R.id.tv_meal_card_proteins);

        tvHeader.setText(titleResId);
        tvCalories.setText(String.valueOf(calories));
        tvCarbohydrates.setText(getResources().getString(R.string.int_g, macros[0]));
        tvFats.setText(getResources().getString(R.string.int_g, macros[1]));
        tvProteins.setText(getResources().getString(R.string.int_g, macros[2]));
    }

    private void animateProgressBar(ProgressBar progressBar, int value) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, value);
        objectAnimator.setInterpolator(mOvershootInterpolator);
        objectAnimator.setDuration(1500);
        objectAnimator.start();
    }
}
