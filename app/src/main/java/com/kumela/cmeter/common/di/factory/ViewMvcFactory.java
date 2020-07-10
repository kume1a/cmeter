package com.kumela.cmeter.common.di.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.kumela.cmeter.ui.adapters.nutrition_details.NutritionDetailsItemMvc;
import com.kumela.cmeter.ui.adapters.nutrition_details.NutritionDetailsItemMvcImpl;
import com.kumela.cmeter.ui.adapters.search.SearchItemMvc;
import com.kumela.cmeter.ui.adapters.search.SearchItemMvcImpl;
import com.kumela.cmeter.ui.common.mvc.ViewMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodViewMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodViewMvcImpl;
import com.kumela.cmeter.ui.screens.app.nutrition.home.NutritionHomeMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsMvcImpl;
import com.kumela.cmeter.ui.screens.app.nutrition.home.NutritionHomeMvcImpl;
import com.kumela.cmeter.ui.screens.app.nutrition.search.SearchMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.search.SearchMvcImpl;
import com.kumela.cmeter.ui.screens.starter.onboarding.OnBoardingViewMvc;
import com.kumela.cmeter.ui.screens.starter.onboarding.OnBoardingViewMvcImpl;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal.WeightGoalViewMvc;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal.WeightGoalViewMvcImpl;
import com.kumela.cmeter.ui.screens.starter.registration.login.LoginMvc;
import com.kumela.cmeter.ui.screens.starter.registration.login.LoginMvcImpl;
import com.kumela.cmeter.ui.screens.starter.registration.register.RegisterMvc;
import com.kumela.cmeter.ui.screens.starter.registration.register.RegisterMvcImpl;

import javax.inject.Inject;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;

    @Inject
    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     *
     * @param mvcClass the class of the required MVC view
     * @param parent   this container will be used as MVC view's parent. See {@link LayoutInflater#inflate(int, ViewGroup)}
     * @param <T>      the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends ViewMvc> T newInstance(Class<T> mvcClass, @Nullable ViewGroup parent) {

        ViewMvc viewMvc;

        if (mvcClass == NutritionHomeMvc.class) {
            viewMvc = new NutritionHomeMvcImpl(mLayoutInflater, parent);
        } else if (mvcClass == AddFoodViewMvc.class) {
            viewMvc = new AddFoodViewMvcImpl(mLayoutInflater, parent);
        } else if (mvcClass == SearchMvc.class) {
            viewMvc = new SearchMvcImpl(mLayoutInflater, parent, this);
        } else if (mvcClass == SearchItemMvc.class) {
            viewMvc = new SearchItemMvcImpl(mLayoutInflater, parent);
        } else if (mvcClass == NutritionDetailsItemMvc.class) {
            viewMvc = new NutritionDetailsItemMvcImpl(mLayoutInflater, parent);
        } else if (mvcClass == NutritionDetailsMvc.class) {
            viewMvc = new NutritionDetailsMvcImpl(mLayoutInflater, parent, this);
        } else if (mvcClass == LoginMvc.class) {
            viewMvc = new LoginMvcImpl(mLayoutInflater, parent);
        } else if (mvcClass == RegisterMvc.class) {
            viewMvc = new RegisterMvcImpl(mLayoutInflater, parent);
        } else if (mvcClass == OnBoardingViewMvc.class) {
            viewMvc = new OnBoardingViewMvcImpl(mLayoutInflater, parent);
        }

        else if (mvcClass == WeightGoalViewMvc.class) {
            viewMvc = new WeightGoalViewMvcImpl(mLayoutInflater, parent);
        }

        else {
            throw new IllegalArgumentException("unsupported MVC view class " + mvcClass);
        }

        //noinspection unchecked
        return (T) viewMvc;
    }

}