package com.kumela.cmeter.common.di;

/**
 * Created by Toko on 29,June,2020
 **/

import android.content.Context;

import com.kumela.cmeter.ui.screens.nutrition.home.NutritionHomeNavController;
import com.kumela.cmeter.ui.common.NavigationController;

import javax.inject.Inject;


public class NavControllerFactory {

    @Inject
    public NavControllerFactory() {
    }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     *
     * @param mControllerClass the class of the required MVC view
     * @param context          the context from where method is called from
     * @param <T>              the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends NavigationController> T newInstance(Class<T> mControllerClass, Context context) {

        NavigationController controller;

        if (mControllerClass == NutritionHomeNavController.class) {
            controller = new NutritionHomeNavController(context);
        } else {
            throw new IllegalArgumentException("unsupported NAV class " + mControllerClass);
        }

        //noinspection unchecked
        return (T) controller;
    }
}