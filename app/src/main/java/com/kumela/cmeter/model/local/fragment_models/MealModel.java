package com.kumela.cmeter.model.local.fragment_models;

import com.kumela.cmeter.model.local.BaseNutrition;
import com.kumela.cmeter.model.local.list.NutritionDetailListModel;
import com.kumela.cmeter.model.local.list.ProductHistoryListModel;

import java.util.List;

/**
 * Created by Toko on 21,July,2020
 **/

public class MealModel {
    private BaseNutrition mBaseNutrition;
    private int mGoalCalories;
    private List<ProductHistoryListModel> mProducts;
    private List<NutritionDetailListModel> mNutritionDetailListModels;

    public MealModel() {
    }

    public int getGoalCalories() {
        return mGoalCalories;
    }

    public int getGoalCarbohydrates() {
        return (int) (getGoalCalories() * .5f / 4f);
    }

    public int getGoalFats() {
        return (int) (getGoalCalories() * .25f / 9f);
    }

    public int getGoalProteins() {
        return (int) (getGoalCalories() * .25f / 4f);
    }

    public BaseNutrition getBaseNutrition() {
        return mBaseNutrition;
    }

    public void setBaseNutrition(BaseNutrition baseNutrition) {
        mBaseNutrition = baseNutrition;
    }

    public void setGoalCalories(int goalCalories) {
        mGoalCalories = goalCalories;
    }

    public List<ProductHistoryListModel> getProducts() {
        return mProducts;
    }

    public void setProducts(List<ProductHistoryListModel> products) {
        mProducts = products;
    }

    public List<NutritionDetailListModel> getNutritionDetailListModels() {
        return mNutritionDetailListModels;
    }

    public void setNutritionDetailListModels(List<NutritionDetailListModel> nutritionDetailListModels) {
        mNutritionDetailListModels = nutritionDetailListModels;
    }
}
