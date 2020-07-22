package com.kumela.cmeter.common.di.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.kumela.cmeter.ui.adapters.added_food.ProductItemMvc;
import com.kumela.cmeter.ui.adapters.added_food.ProductItemMvcImpl;
import com.kumela.cmeter.ui.adapters.nutrition_details.NutritionDetailsItemMvc;
import com.kumela.cmeter.ui.adapters.nutrition_details.NutritionDetailsItemMvcImpl;
import com.kumela.cmeter.ui.adapters.search.SearchItemMvc;
import com.kumela.cmeter.ui.adapters.search.SearchItemMvcImpl;
import com.kumela.cmeter.ui.common.mvc.ViewMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodViewMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.add_food.AddFoodViewMvcImpl;
import com.kumela.cmeter.ui.screens.app.nutrition.home.NutritionHomeMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.home.NutritionHomeMvcImpl;
import com.kumela.cmeter.ui.screens.app.nutrition.meal.MealViewMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.meal.MealViewMvcImpl;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details.NutritionDetailsMvcImpl;
import com.kumela.cmeter.ui.screens.app.nutrition.search.SearchMvc;
import com.kumela.cmeter.ui.screens.app.nutrition.search.SearchMvcImpl;
import com.kumela.cmeter.ui.screens.starter.authentication.login.LoginMvc;
import com.kumela.cmeter.ui.screens.starter.authentication.login.LoginMvcImpl;
import com.kumela.cmeter.ui.screens.starter.authentication.register.RegisterMvc;
import com.kumela.cmeter.ui.screens.starter.authentication.register.RegisterMvcImpl;
import com.kumela.cmeter.ui.screens.starter.onboarding.OnBoardingViewMvc;
import com.kumela.cmeter.ui.screens.starter.onboarding.OnBoardingViewMvcImpl;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.active.ActiveViewMvc;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.active.ActiveViewMvcImpl;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.goal.GoalViewMvc;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.goal.GoalViewMvcImpl;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.info.InfoViewMvc;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.info.InfoViewMvcImpl;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal.WeightGoalViewMvc;
import com.kumela.cmeter.ui.screens.starter.onboarding.tabs.fragments.weight_goal.WeightGoalViewMvcImpl;

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
     * @param mvcClass  the class of the required MVC view
     * @param container this container will be used as MVC view's container. See {@link LayoutInflater#inflate(int, ViewGroup)}
     * @param <T>       the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends ViewMvc> T newInstance(Class<T> mvcClass, @Nullable ViewGroup container) {

        ViewMvc viewMvc;

        if (mvcClass == NutritionHomeMvc.class) {
            viewMvc = new NutritionHomeMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == AddFoodViewMvc.class) {
            viewMvc = new AddFoodViewMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == SearchMvc.class) {
            viewMvc = new SearchMvcImpl(mLayoutInflater, container, this);
        } else if (mvcClass == SearchItemMvc.class) {
            viewMvc = new SearchItemMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == NutritionDetailsItemMvc.class) {
            viewMvc = new NutritionDetailsItemMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == NutritionDetailsMvc.class) {
            viewMvc = new NutritionDetailsMvcImpl(mLayoutInflater, container, this);
        } else if (mvcClass == ProductItemMvc.class) {
            viewMvc = new ProductItemMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == LoginMvc.class) {
            viewMvc = new LoginMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == RegisterMvc.class) {
            viewMvc = new RegisterMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == OnBoardingViewMvc.class) {
            viewMvc = new OnBoardingViewMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == InfoViewMvc.class) {
            viewMvc = new InfoViewMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == GoalViewMvc.class) {
            viewMvc = new GoalViewMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == ActiveViewMvc.class) {
            viewMvc = new ActiveViewMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == WeightGoalViewMvc.class) {
            viewMvc = new WeightGoalViewMvcImpl(mLayoutInflater, container);
        } else if (mvcClass == MealViewMvc.class) {
            viewMvc = new MealViewMvcImpl(mLayoutInflater, container, this);
        } else {
            throw new IllegalArgumentException("unsupported MVC view class " + mvcClass);
        }

        //noinspection unchecked
        return (T) viewMvc;
    }

}