package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.BaseViewMvc;
import com.kumela.cmeter.ui.common.util.ToolbarHelper;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.AddFoodPagerAdapter;

/**
 * Created by Toko on 26,June,2020
 **/

public class AddFoodViewMvcImpl extends BaseViewMvc implements AddFoodViewMvc {

    private static final int animationDuration = 1000;

    private final float screenWidth;
    private final float screenHeight;

    public AddFoodViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.add_food_fragment, container, false));

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        screenWidth = (float) displayMetrics.widthPixels;
        screenHeight = (float) displayMetrics.heightPixels;
    }

    @Override
    public int[] getSearchPosition(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    @Override
    public void setupViewPager(FragmentManager supportFragmentManager, Lifecycle lifecycle, String meal) {
        ViewPager2 viewPager = findViewById(R.id.vp_add_food);
        viewPager.setAdapter(new AddFoodPagerAdapter(
                supportFragmentManager,
                lifecycle,
                meal
        ));

        TabLayout tabLayout = findViewById(R.id.tabs_add_food);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_recent);
                    tab.setText(R.string.tabs_recent);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_favorite);
                    tab.setText(R.string.tabs_favorite);
                    break;
            }
        }).attach();
    }

    @Override
    public void setupToolbar(FragmentActivity activity, @StringRes int title) {
        ((ToolbarHelper) activity).setTitle(getResources().getString(title));
    }

    @Override
    public void startCircularRevealAnimation(int cx, int cy) {
        View v = getRootView();

        // Define Circular reveal animation
        // Use the diagonal of the root view
        float finalRadius = (float) Math.sqrt(screenWidth * screenWidth + screenHeight * screenHeight);
        Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 20f, finalRadius);
        anim.setDuration(animationDuration);
        anim.setInterpolator(new FastOutSlowInInterpolator());

        // Start both animations together
        anim.start();
        getBackgroundColorValueAnimator(v).start();
    }

    @Override
    public void startCircularRevealExitAnimation(int endX, int endY) {

        getRootView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                float initRadius = (float) Math.sqrt(screenWidth * screenWidth + screenHeight * screenHeight);
                Animator anim = ViewAnimationUtils.createCircularReveal(v, endX, endY, initRadius, 0);
                anim.setDuration(animationDuration);
                anim.setInterpolator(new FastOutSlowInInterpolator());

                anim.start();
                getBackgroundColorValueAnimator(v).start();
            }
        });

//        View v = getRootView();

//        float initRadius = (float) Math.sqrt(screenWidth * screenWidth + screenHeight * screenHeight);
//        Animator anim = ViewAnimationUtils.createCircularReveal(v, endX, endY, initRadius, 0);
//        anim.setDuration(animationDuration);
//        anim.setInterpolator(new FastOutSlowInInterpolator());
////        anim.addListener(new AnimatorListenerAdapter() {
////            @Override
////            public void onAnimationEnd(Animator animation) {
////                //Important: This will prevent the view's flashing (visible between the finished animation and the Fragment remove)
////                v.setVisibility(View.GONE);
////            }
////        });
//
//        anim.start();
//        getBackgroundColorValueAnimator(v).start();
    }

    private ValueAnimator getBackgroundColorValueAnimator(View v) {
        int startColor = ContextCompat.getColor(getContext(), R.color.colorAccentSecondary80);
        int endColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);

        ValueAnimator colorAnimator = new ValueAnimator();
        colorAnimator.setIntValues(startColor, endColor);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(animationDuration);
        colorAnimator.addUpdateListener(valueAnimator -> v.setBackgroundColor((Integer) valueAnimator.getAnimatedValue()));

        return colorAnimator;
    }
}
