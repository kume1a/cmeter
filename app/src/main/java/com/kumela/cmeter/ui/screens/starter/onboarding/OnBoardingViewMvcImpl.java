package com.kumela.cmeter.ui.screens.starter.onboarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.kumela.cmeter.R;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvcImpl;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.OnBoardingPagerAdapter;

/**
 * Created by Toko on 09,July,2020
 **/

public class OnBoardingViewMvcImpl extends BaseObservableViewMvcImpl<OnBoardingViewMvc.Listener>
        implements OnBoardingViewMvc {

    private MaterialButton btnNext, btnBack;
    private ViewPager2 mViewPager;

    private AppCompatImageView[] indicators;


    public OnBoardingViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.onboarding_fragment, parent, false));

        mViewPager = findViewById(R.id.vp_onboarding);

        btnNext = findViewById(R.id.btn_onboarding_next);
        btnBack = findViewById(R.id.btn_onboarding_back);

        indicators = new AppCompatImageView[4];
        indicators[0] = findViewById(R.id.iv_onboarding_indicator1);
        indicators[1] = findViewById(R.id.iv_onboarding_indicator2);
        indicators[2] = findViewById(R.id.iv_onboarding_indicator3);
        indicators[3] = findViewById(R.id.iv_onboarding_indicator4);

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for (Listener listener : getListeners()) listener.onPageChanged();
            }
        });

        btnNext.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onNextClick();
        });
        btnBack.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onBackClick();
        });
    }

    @Override
    public void setPagerAdapter(AppCompatActivity activity) {
        mViewPager.setAdapter(new OnBoardingPagerAdapter(activity.getSupportFragmentManager(), activity.getLifecycle()));
    }

    @Override
    public void nextPage() {
        if (mViewPager.getCurrentItem() != 3) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
        } else for (Listener listener : getListeners()) listener.onFinished();
    }

    @Override
    public void previousPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    @Override
    public void onPageChanged() {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                btnBack.setVisibility(View.INVISIBLE);
                break;
            case 1:
                btnBack.setVisibility(View.VISIBLE);
            case 2:
                btnNext.setText(getResources().getString(R.string.onboarding_next));
                break;
            case 3:
                btnNext.setText(getResources().getString(R.string.onboarding_finish));
                break;
        }

        for (int i = 0; i < indicators.length; i++) {
            int drawableId;
            if (mViewPager.getCurrentItem() == i) {
                drawableId = R.drawable.item_onboarding_slider_active;
            } else drawableId = R.drawable.item_onboarding_slider_inactive;

            indicators[i].setBackgroundDrawable(ContextCompat.getDrawable(getContext(), drawableId));
        }
    }
}
