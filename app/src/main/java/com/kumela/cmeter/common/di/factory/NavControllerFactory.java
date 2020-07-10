package com.kumela.cmeter.common.di.factory;


import android.content.Context;
import android.view.View;

import com.kumela.cmeter.ui.common.nav.ActivityNavController;
import com.kumela.cmeter.ui.common.nav.BaseNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.home.NutritionHomeNavController;
import com.kumela.cmeter.ui.screens.app.nutrition.search.SearchNavController;
import com.kumela.cmeter.ui.screens.starter.registration.login.LoginNavController;
import com.kumela.cmeter.ui.screens.starter.registration.register.RegisterNavController;

import javax.inject.Inject;


/**
 * Created by Toko on 29,June,2020
 **/


public class NavControllerFactory {

    @Inject
    public NavControllerFactory() {
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
    public <T extends BaseNavController> T newInstance(Class<T> controllerClass, View view) {

        BaseNavController controller;

        if (controllerClass == NutritionHomeNavController.class) {
            controller = new NutritionHomeNavController(view);
        } else if (controllerClass == RegisterNavController.class) {
            controller = new RegisterNavController(view);
        } else if (controllerClass == LoginNavController.class) {
            controller = new LoginNavController(view);
        } else {
            throw new IllegalArgumentException("Unsupported NavController class " + controllerClass);
        }

        //noinspection unchecked
        return (T) controller;
    }

    public <T extends ActivityNavController> T newInstance(Class<T> controllerClass, Context context) {
        ActivityNavController controller;

        if (controllerClass == SearchNavController.class) {
            controller = new SearchNavController(context);
        } else if (controllerClass == AddFoodNavController.class) {
            controller = new AddFoodNavController(context);
        } else {
            throw new IllegalArgumentException("Unsupported NavController class " + controllerClass);
        }

        //noinspection unchecked
        return (T) controller;
    }
}