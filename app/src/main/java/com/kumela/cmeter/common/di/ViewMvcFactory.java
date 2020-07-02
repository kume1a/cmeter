package com.kumela.cmeter.common.di;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.kumela.cmeter.ui.adapters.search.SearchItemMvc;
import com.kumela.cmeter.ui.adapters.search.SearchItemMvcImpl;
import com.kumela.cmeter.ui.common.mvc.ViewMvc;
import com.kumela.cmeter.ui.screens.nutrition.add_food.AddFoodMvc;
import com.kumela.cmeter.ui.screens.nutrition.home.NutritionHomeMvcImpl;
import com.kumela.cmeter.ui.screens.nutrition.search.SearchMvc;
import com.kumela.cmeter.ui.screens.nutrition.search.SearchMvcImpl;

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

        ViewMvc controller;

        if (mvcClass == NutritionHomeMvcImpl.class) {
            controller = new NutritionHomeMvcImpl(mLayoutInflater, parent);
        } else if (mvcClass == AddFoodMvc.class) {
            controller = new AddFoodMvc(mLayoutInflater, parent);
        } else if (mvcClass == SearchMvc.class) {
            controller = new SearchMvcImpl(mLayoutInflater, parent, this);
        } else if (mvcClass == SearchItemMvc.class) {
            controller = new SearchItemMvcImpl(mLayoutInflater, parent);
        } else {
            throw new IllegalArgumentException("unsupported MVC view class " + mvcClass);
        }

        //noinspection unchecked
        return (T) controller;
    }

}