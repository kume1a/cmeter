package com.kumela.cmeter.common.di.factory;


import android.content.Context;
import android.view.View;

import com.kumela.cmeter.ui.common.nav.DualNavController;
import com.kumela.cmeter.ui.common.nav.FragmentNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.tabs.tab_fragment.TabNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.home.NutritionHomeNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.search.SearchNavController;
import com.kumela.cmeter.ui.screens.starter.authentication.login.LoginNavController;
import com.kumela.cmeter.ui.screens.starter.authentication.register.RegisterNavController;
import com.kumela.cmeter.ui.screens.starter.onboarding.OnBoardingNavController;

import javax.inject.Inject;


/**
 * Created by Toko on 29,June,2020
 **/


public class NavControllerFactory {

    private Context mContext;

    @Inject
    public NavControllerFactory(Context context) {
        this.mContext = context;
    }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     *
     * @param controllerClass the class of the required MVC view
     * @param view            the view from onViewCreated method
     * @param <T>             the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends FragmentNavController> T newInstance(Class<T> controllerClass, View view) {

        FragmentNavController controller;

        if (controllerClass == NutritionHomeNavController.class) {
            controller = new NutritionHomeNavController(view);
        } else if (controllerClass == RegisterNavController.class) {
            controller = new RegisterNavController(view);
        } else if (controllerClass == AddFoodNavController.class) {
            controller = new AddFoodNavController(view);
        } else if (controllerClass == SearchNavController.class) {
            controller = new SearchNavController(view);
        } else if (controllerClass == TabNavController.class) {
            controller = new TabNavController(view);
        } else if (controllerClass == NutritionDetailsNavController.class) {
            controller = new NutritionDetailsNavController(view);
        } else {
            throw new IllegalArgumentException("Unsupported NavController class " + controllerClass);
        }

        //noinspection unchecked
        return (T) controller;
    }

    public <T extends DualNavController> T newInstance(Class<T> controllerClass, Context context, View view) {
        DualNavController controller;

        if (controllerClass == LoginNavController.class) {
            controller = new LoginNavController(context, view);
        } else if (controllerClass == OnBoardingNavController.class) {
            controller = new OnBoardingNavController(context, view);
        } else
            throw new IllegalArgumentException("Unsupported NavController class " + controllerClass);

        //noinspection unchecked
        return (T) controller;
    }
}