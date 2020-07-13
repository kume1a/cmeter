package com.kumela.cmeter.ui.screens.starter.onboarding;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.R;
import com.kumela.cmeter.model.firebase.User;
import com.kumela.cmeter.network.firebase.FirebaseUserHandler;
import com.kumela.cmeter.ui.common.base.BaseFragment;

public class OnBoardingFragment extends BaseFragment implements OnBoardingViewMvc.Listener, FirebaseUserHandler.Listener {

    private static final String TAG = "OnBoardingFragment";

    private OnBoardingViewMvc mViewMvc;
    private OnBoardingViewModel mViewModel;
    private OnBoardingNavController mNavController;
    private FirebaseUserHandler mFirebaseUserHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getViewMvcFactory().newInstance(OnBoardingViewMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(OnBoardingViewModel.class);
        mNavController = getNavControllerFactory().newInstance(OnBoardingNavController.class, getContext(), view);
        mFirebaseUserHandler = getPresentationComponent().getFirebaseUserHandler();

        mViewMvc.setPagerAdapter(getChildFragmentManager(), getLifecycle());
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFirebaseUserHandler.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFirebaseUserHandler.unregisterListener(this);
    }

    @Override
    public void onNextClick() {
        onPageChanged();
        mViewMvc.nextPage();
    }

    @Override
    public void onBackClick() {
        onPageChanged();
        mViewMvc.previousPage();
    }

    @Override
    public void onPageChanged() {
        mViewMvc.onPageChanged();
    }

    @Override
    public void onFinished() {
        Log.d(TAG, "onFinished: viewModel = " + mViewModel.toString());

        boolean validToGo = true;
        if (mViewModel.getGoalLiveData().getValue() == OnBoardingViewModel.Goal.LOSE_WEIGHT) {
            if (mViewModel.getGoalWeight() >= mViewModel.getCurrentWeight()) {
                validToGo = false;
                mNavController.actionOpenDialog(R.string.alert_message_invalid_weight_input_lose);
            }
        } else {
            // GAIN_WEIGHT
            if (mViewModel.getGoalWeight() <= mViewModel.getCurrentWeight()) {
                validToGo = false;
                mNavController.actionOpenDialog(R.string.alert_message_invalid_weight_input_gain);
            }
        }

        if (validToGo) {
            final int bmr = mViewModel.getBMR();
            final int waterIntake = mViewModel.getDailyWaterIntake();
            final int dailyExtra = mViewModel.getDailyExtra();

            Log.d(TAG, "onFinished: bmr = " + bmr + " calories");
            Log.d(TAG, "onFinished: water intake = " + waterIntake + " litres");
            Log.d(TAG, "onFinished: weekly extra = " + dailyExtra  + " calories");

            final OnBoardingFragmentArgs args = OnBoardingFragmentArgs.fromBundle(requireArguments());
            mFirebaseUserHandler.createUserAndNotify(new User(
                    mFirebaseUserHandler.getUid(),
                    args.getUsername(),
                    args.getEmail(),
                    bmr,
                    dailyExtra,
                    waterIntake
            ));
        }
    }

    @Override
    public void onUserCreated() {
        mNavController.actionToApp(mFirebaseUserHandler.getUid());
    }

    @Override
    public void onUserCreateFailed(@StringRes int errorMsgId) {
        mViewMvc.showErrorSnackBar(errorMsgId);
    }
}