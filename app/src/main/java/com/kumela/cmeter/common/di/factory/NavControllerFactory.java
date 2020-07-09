package com.kumela.cmeter.common.di.factory;

/**
 * Created by Toko on 29,June,2020
 **/

import android.content.Context;

import com.kumela.cmeter.ui.common.ContextWrapper;
import com.kumela.cmeter.ui.screens.nutrition.home.NutritionHomeNavController;
import com.kumela.cmeter.ui.screens.nutrition.search.SearchNavController;
import com.kumela.cmeter.ui.screens.registration.login.LoginNavController;
import com.kumela.cmeter.ui.screens.registration.register.RegisterNavController;

import javax.inject.Inject;


public class NavControllerFactory {

    @Inject
    public NavControllerFactory() {
    }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     *
     * @param controllerClass the class of the required MVC view
     * @param context         the context from where method is called from
     * @param <T>             the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends ContextWrapper> T newInstance(Class<T> controllerClass, Context context) {

        ContextWrapper controller;

        if (controllerClass == NutritionHomeNavController.class) {
            controller = new NutritionHomeNavController(context);
        } else if (controllerClass == SearchNavController.class) {
            controller = new SearchNavController(context);
        } else if (controllerClass == LoginNavController.class) {
            controller = new LoginNavController(context);
        } else if (controllerClass == RegisterNavController.class) {
            controller = new RegisterNavController(context);
        }
        else {
            throw new IllegalArgumentException("unsupported NAV class " + controllerClass);
        }

        //noinspection unchecked
        return (T) controller;
    }
}