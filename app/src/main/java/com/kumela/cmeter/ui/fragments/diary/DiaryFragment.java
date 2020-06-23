package com.kumela.cmeter.ui.fragments.diary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kumela.cmeter.R;

public class DiaryFragment extends Fragment {

    private FloatingActionButton mFabMain, mFabBreakfast, mFabDinner, mFabSupper, mFabSnacks;
    private CardView mCvBreakfast, mCvDinner, mCvSupper, mCvSnacks;

    private OvershootInterpolator interpolator = new OvershootInterpolator();
    private boolean isMenuOpen = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diary, container, false);

        mFabMain = v.findViewById(R.id.fab_main);
        mFabBreakfast = v.findViewById(R.id.fab_breakfast);
        mFabDinner = v.findViewById(R.id.fab_dinner);
        mFabSupper = v.findViewById(R.id.fab_supper);
        mFabSnacks = v.findViewById(R.id.fab_snacks);

        mCvBreakfast = v.findViewById(R.id.cv_diary_breakfast);
        mCvDinner = v.findViewById(R.id.cv_diary_dinner);
        mCvSupper = v.findViewById(R.id.cv_diary_supper);
        mCvSnacks = v.findViewById(R.id.cv_diary_snacks);

        mFabMain.setOnClickListener(mMenuListener);
        mFabBreakfast.setOnClickListener(mMenuListener);
        mFabDinner.setOnClickListener(mMenuListener);
        mFabSupper.setOnClickListener(mMenuListener);
        mFabSnacks.setOnClickListener(mMenuListener);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        DiaryViewModel viewModel = new ViewModelProvider(this).get(DiaryViewModel.class);
        // TODO: Use the ViewModel
    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        mFabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

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

    private View.OnClickListener mMenuListener = v -> {
        int id = v.getId();
        if (id == R.id.fab_main) {
            if (isMenuOpen) closeMenu();
            else openMenu();
        } else {
            String title = "";
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

            closeMenu();
            NavDirections navDirection = DiaryFragmentDirections.actionNavDiaryToAddFoodActivity(title);
            Navigation.findNavController(v).navigate(navDirection);
        }
    };

}