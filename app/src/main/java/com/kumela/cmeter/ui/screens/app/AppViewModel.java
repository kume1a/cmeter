package com.kumela.cmeter.ui.screens.app;

import androidx.lifecycle.ViewModel;

/**
 * Created by Toko on 03,August,2020
 **/

public class AppViewModel extends ViewModel {
    private boolean mPlayAddFoodAnimation = false;

    public void setPlayAddFoodAnimation(boolean playAddFoodAnimation) {
        if (this.mPlayAddFoodAnimation != playAddFoodAnimation) {
            this.mPlayAddFoodAnimation = playAddFoodAnimation;
        }
    }

    public boolean getPlayAddFoodAnimation() {
        return mPlayAddFoodAnimation;
    }
}
