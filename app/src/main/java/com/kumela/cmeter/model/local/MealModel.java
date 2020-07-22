package com.kumela.cmeter.model.local;

import java.util.List;

/**
 * Created by Toko on 21,July,2020
 **/

public class MealModel {
    private BaseNutrition mBaseNutrition;
    private int mGoalCalories;
    private List<ProductModel> mProducts;
    private List<NutritionDetailItem> mNutritionDetailItems;

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

    public List<ProductModel> getProducts() {
        return mProducts;
    }

    public void setProducts(List<ProductModel> products) {
        mProducts = products;
    }

    public List<NutritionDetailItem> getNutritionDetailItems() {
        return mNutritionDetailItems;
    }

    public void setNutritionDetailItems(List<NutritionDetailItem> nutritionDetailItems) {
        mNutritionDetailItems = nutritionDetailItems;
    }
}
