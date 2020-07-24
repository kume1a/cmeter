package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.firebase.AddedFood;
import com.kumela.cmeter.model.local.BaseNutrition;
import com.kumela.cmeter.model.local.MealModel;
import com.kumela.cmeter.model.local.NutritionDetailItem;
import com.kumela.cmeter.model.local.ProductModel;
import com.kumela.cmeter.ui.common.util.NutritionInfoParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Toko on 21,July,2020
 **/

public class MealViewModel extends ViewModel implements NutritionInfoParser.Listener {

    private static final String TAG = "MealViewModel";

    interface Listener {
        void noProductsAdded();

        void onProvideMealModel(MealModel mealModel);

        void onProvideMealModelFailed();
    }

    private Set<Listener> mListeners = new HashSet<>(1);

    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    private final String mUserId;
    private final FirebaseFirestore mFirebaseFirestore;
    private final NutritionInfoParser mNutritionInfoParser;

    private MealModel mMealModel;

    public MealViewModel(String uid, FirebaseFirestore firebaseFirestore, NutritionInfoParser nutritionInfoParser) {
        this.mUserId = uid;
        this.mFirebaseFirestore = firebaseFirestore;
        this.mNutritionInfoParser = nutritionInfoParser;

        mMealModel = new MealModel();

        mNutritionInfoParser.registerListener(this);
    }

    private void notifyControllerIAllFieldsPresent() {
        if (mMealModel.getBaseNutrition() != null &&
                mMealModel.getNutritionDetailItems() != null &&
                mMealModel.getProducts() != null &&
                mMealModel.getGoalCalories() != 0f) {
            for (Listener listener : mListeners) listener.onProvideMealModel(mMealModel);
        }
    }

    private void notifyFailure() {
        for (Listener listener : mListeners) listener.onProvideMealModelFailed();
    }

    void fetchMealModelAndNotify(@NonNull String meal, int dailyGoalCalories) {
        float mealGoalCalories;
        switch (meal) {
            case Constants.BREAKFAST:
                mealGoalCalories = dailyGoalCalories * .3f;
                break;
            case Constants.DINNER:
                mealGoalCalories = dailyGoalCalories * .4f;
                break;
            case Constants.SUPPER:
                mealGoalCalories = dailyGoalCalories * .25f;
                break;
            case Constants.SNACKS:
                mealGoalCalories = dailyGoalCalories * .05f;
                break;
            default:
                throw new RuntimeException("arg meal should be one of the following Constants.BREAKFAST, Constants.DINNER, Constants.SUPPER, Constants.SNACKS");
        }
        mMealModel.setGoalCalories((int) mealGoalCalories);
        fetchCurrentMealFoodsAndParseResult(meal);
    }

    private void fetchCurrentMealFoodsAndParseResult(@NonNull String meal) {
        mFirebaseFirestore.collection(Constants.COLLECTION_PRODUCTS)
                .whereEqualTo(Constants.UID, mUserId)
                .whereEqualTo(Constants.MEAL, meal)
                .whereEqualTo(Constants.DATE, Utils.getDate())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<AddedFood> addedFoods = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        addedFoods.add(documentSnapshot.toObject(AddedFood.class));
                    }

                    if (addedFoods.size() == 0) {
                        for (Listener listener : mListeners) listener.noProductsAdded();
                        return;
                    }
                    parseBaseNutritionAndProductModels(addedFoods);
                    mNutritionInfoParser.parseNutritionInfoAndNotify(addedFoods);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onCancelled: ", e);
                    notifyFailure();
                });
    }

    @Override
    public void onNutritionInfoParsed(List<NutritionDetailItem> nutritionDetails) {
        mMealModel.setNutritionDetailItems(nutritionDetails);
        notifyControllerIAllFieldsPresent();
    }

    @Override
    public void onNutritionInfoParseFailed() {
        Log.e(TAG, "onNutritionInfoParseFailed: called");
        notifyFailure();
    }

    private void parseBaseNutritionAndProductModels(ArrayList<AddedFood> addedFoods) {
        List<ProductModel> productModels = new ArrayList<>();
        float calories = 0, carbohydrates = 0, fats = 0, proteins = 0;

        for (AddedFood addedFood : addedFoods) {
            calories += addedFood.totalCalories;
            carbohydrates += addedFood.totalCarbohydrates;
            fats += addedFood.totalFats;
            proteins += addedFood.totalProteins;

            productModels.add(new ProductModel(
                    addedFood.foodName, addedFood.currentServingQuantity,
                    addedFood.servingUnit, (int) addedFood.totalCalories
            ));
        }

        mMealModel.setBaseNutrition(new BaseNutrition(calories, carbohydrates, fats, proteins));
        mMealModel.setProducts(productModels);

        notifyControllerIAllFieldsPresent();
    }
}
