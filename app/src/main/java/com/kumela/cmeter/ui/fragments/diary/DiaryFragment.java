package com.kumela.cmeter.ui.fragments.diary;

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

        mFabBreakfast.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        mFabDinner.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        mFabSupper.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        mFabSnacks.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

        mCvBreakfast.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        mCvDinner.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        mCvSupper.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        mCvSnacks.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        // TODO: 6/22/2020 make view gone on close menu and visible on showMenu

        mFabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        float translationY = 100f;
        mFabBreakfast.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        mFabDinner.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        mFabSupper.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        mFabSnacks.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

        mCvBreakfast.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        mCvDinner.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        mCvSupper.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        mCvSnacks.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
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