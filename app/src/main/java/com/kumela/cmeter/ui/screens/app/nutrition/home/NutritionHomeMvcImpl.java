package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.local.fragment_models.NutritionHomeModel;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.List;

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

    private ProgressBar mPbCalories;
    private MaterialTextView mTvCalorieProgress;

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
    private CardView mCardDashboard;
    private CardView mCardWeight;

    private AppCompatImageView ivLoading;
    private AppCompatImageView ivLoadingExit;
    private Animatable animationLoading;

    public NutritionHomeMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.nutrition_home_fragment, container, false));

        // setting up vector drawable animation
        ivLoading = findViewById(R.id.loading_nutrition_home);
        ivLoadingExit = findViewById(R.id.loading_nutrition_home_exit);
        Drawable loadingDrawable = ivLoading.getDrawable();
        animationLoading = (Animatable) loadingDrawable;

        AnimatedVectorDrawableCompat.registerAnimationCallback(
                ivLoading.getDrawable(),
                new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        super.onAnimationEnd(drawable);
                        for (Listener listener : getListeners()) {
                            listener.onLoadingAnimationEnd();
                        }
                    }
                });

        mPbCalories = findViewById(R.id.pb_nutrition_home_dashboard_calories);
        mTvCalorieProgress = findViewById(R.id.tv_nutrition_home_dashboard_progress_calories);

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

        mCardDashboard = findViewById(R.id.cv_nutrition_home_calorie_dashboard);
        mCardWeight = findViewById(R.id.cv_nutrition_home_weight);

        mCardBreakfast = findViewById(R.id.cv_nutrition_home_breakfast);
        mCardDinner = findViewById(R.id.cv_nutrition_home_dinner);
        mCardSupper = findViewById(R.id.cv_nutrition_home_supper);
        mCardSnacks = findViewById(R.id.cv_nutrition_home_snacks);

        final float startingTranslationY = 150f;
        mCardDashboard.setTranslationY(startingTranslationY);
        mCardWeight.setTranslationY(startingTranslationY);
        mCardBreakfast.setTranslationY(startingTranslationY);
        mCardDinner.setTranslationY(startingTranslationY);
        mCardSupper.setTranslationY(startingTranslationY);
        mCardSnacks.setTranslationY(startingTranslationY);

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
    public void startLoadingAnimation() {
        animationLoading.start();
    }

    @Override
    public void startLoadingExitAnimation() {
        ivLoading.setVisibility(View.GONE);
        ivLoadingExit.setVisibility(View.VISIBLE);

        Drawable loadingExitDrawable = ivLoadingExit.getDrawable();
        Animatable loadingExitAnimation = (Animatable) loadingExitDrawable;
        AnimatedVectorDrawableCompat.registerAnimationCallback(
                loadingExitDrawable,
                new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        for (Listener listener : getListeners()) {
                            listener.onLoadingExitAnimationEnd();
                        }
                    }
                }
        );
        loadingExitAnimation.start();
    }

    @Override
    public void revealHomeData() {
        long duration = 600L;
        animateHomeReveal(mCardDashboard, duration);
        animateHomeReveal(mCardWeight, duration + 250L);
        animateHomeReveal(mCardBreakfast, duration + 150L);
        animateHomeReveal(mCardDinner, duration + 150L);
        animateHomeReveal(mCardSupper, duration + 150L);
        animateHomeReveal(mCardSnacks, duration + 150L);

        mCardDashboard.setVisibility(View.VISIBLE);
        mCardWeight.setVisibility(View.VISIBLE);
        mCardBreakfast.setVisibility(View.VISIBLE);
        mCardDinner.setVisibility(View.VISIBLE);
        mCardSupper.setVisibility(View.VISIBLE);
        mCardSnacks.setVisibility(View.VISIBLE);
    }

    private void animateHomeReveal(View v, long duration) {
        v.animate()
                .setDuration(duration)
                .translationY(0f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(1f)
                .start();
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

        mFabMain.show();

        mFabMain.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onFabClick(isMenuOpen);
        });
        mFabBreakfast.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(v, Constants.BREAKFAST);
        });
        mFabDinner.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(v, Constants.DINNER);
        });
        mFabSupper.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(v, Constants.SUPPER);
        });
        mFabSnacks.setOnClickListener(v -> {
            for (Listener listener : getListeners())
                listener.onMenuClick(v, Constants.SNACKS);
        });
    }

    @Override
    public void hideDimmer() {
        mViewDim.setVisibility(View.GONE);
    }

    @Override
    public void hideFabMenu(View fabMenu) {
        if (mFabMain != null) {
            mFabMain.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    fabMenu.setVisibility(View.GONE);
                }
            });
        }

        if (mViewDim != null) {
            mViewDim.setOnClickListener(null);
        }
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

    @Override
    public void animateViewToCenter(@NonNull View v, @NonNull String meal) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        final float centerX = (float) displayMetrics.widthPixels / 2 - (float) v.getWidth() / 2;
        final float centerY = (float) displayMetrics.heightPixels / 2 + (float) v.getHeight() / 2 - 50;

        final float startX = v.getX();
        final float startY = v.getY();

        final long duration = 400;

        ValueAnimator positionAnimator = ValueAnimator.ofFloat(0, 1);
        positionAnimator.setDuration(duration);
        positionAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        positionAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();

            float x = centerX + (startX - centerX - ((startX - centerX) * value));
            float y = centerY + (startY - centerY - ((startY - centerY) * (value * value)));

            v.setX(x);
            v.setY(y);

            if (value > .5f) {
                float scale = (1 - value) * 2;

                v.setScaleX(scale);
                v.setScaleY(scale);
            }
        });
        positionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                for (Listener listener : getListeners())
                    listener.onFabAnimationEnded(v, startX, meal);
            }
        });
        positionAnimator.start();
    }

    /**
     * Hides rest of menu including cards and floating action buttons
     *
     * @param view floating action button that is pressed
     */
    @Override
    public void hideRestOfMenu(@NonNull View view) {
        List<View> viewsToHide = new ArrayList<>(9);
        viewsToHide.add(mFabMain);
        viewsToHide.add(mFabBreakfast);
        viewsToHide.add(mFabDinner);
        viewsToHide.add(mFabSupper);
        viewsToHide.add(mFabSnacks);
        viewsToHide.add(mCvBreakfast);
        viewsToHide.add(mCvDinner);
        viewsToHide.add(mCvSupper);
        viewsToHide.add(mCvSnacks);

        viewsToHide.remove(view);

        for (View v : viewsToHide) v.animate().alpha(0f).setDuration(300).start();
    }

    @Override
    public void resetFabToInitialPosition(View v, float startX) {
        v.setX(startX);
        v.setTranslationY(100f);
        v.setScaleX(1f);
        v.setScaleY(1f);
        v.setVisibility(View.GONE);

        mFabMain.setRotation(0f);
    }

    private void setupCalorieDashboardPie(int goalCalories, int currentCalories) {
        final int calorieProgress = (int) ((float) currentCalories / goalCalories * 100);

        animateProgressBar(mPbCalories, calorieProgress);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, calorieProgress);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(mOvershootInterpolator);
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            mTvCalorieProgress.setText(getResources().getString(R.string.value_percent, value));
        });
        valueAnimator.start();
    }

    @Override
    public void bindHomeModelInfo(NutritionHomeModel info) {
        Log.d(getClass().getSimpleName(), "bindHomeModelInfo: called, info = " + info);

        // dashboard
        mTvCurrentCalories.setText(String.valueOf(info.currentCalories));
        setupCalorieDashboardPie(info.goalCalories, info.currentCalories);

        mTvGoalCalories.setText(getResources().getString(R.string.nutrition_dashboard_goal_calories, info.goalCalories));

        mTvCarbohydratesProgress.setText(getResources().getString(R.string.value_slash_value_g, info.carbohydrates, info.goalCarbohydrates));
        mTvFatsProgress.setText(getResources().getString(R.string.value_slash_value_g, info.fats, info.goalFats));
        mTvProteinProgress.setText(getResources().getString(R.string.value_slash_value_g, info.proteins, info.goalProteins));

        mPbCarbohydrates.setMax(info.goalCarbohydrates);
        mPbFats.setMax(info.goalFats);
        mPbProteins.setMax(info.goalProteins);

        animateProgressBar(mPbCarbohydrates, info.carbohydrates);
        animateProgressBar(mPbFats, info.fats);
        animateProgressBar(mPbProteins, info.proteins);

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
