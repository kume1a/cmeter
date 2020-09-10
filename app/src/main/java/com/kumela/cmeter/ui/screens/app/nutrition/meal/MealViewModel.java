package com.kumela.cmeter.ui.screens.app.nutrition.meal;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kumela.cmeter.model.firebase.FirebaseFoodNutrients;
import com.kumela.cmeter.model.local.fragment_models.MealModel;

import java.util.ArrayList;

/**
 * Created by Toko on 21,July,2020
 **/

public class MealViewModel extends ViewModel {

    private static final String TAG = "MealViewModel";

    private MutableLiveData<MealModel> mMealModelMutableLiveData;

    private final String mUserId;
    private final FirebaseFirestore mFirebaseFirestore;

    public MealViewModel(String uid, FirebaseFirestore firebaseFirestore) {
        this.mUserId = uid;
        this.mFirebaseFirestore = firebaseFirestore;

        mMealModelMutableLiveData = new MutableLiveData<>();
    }

    private void notifyControllerIAllFieldsPresent() {
//        if (mMealModel.getBaseNutrition() != null &&
//                mMealModel.getNutritionDetailListModels() != null &&
//                mMealModel.getProducts() != null &&
//                mMealModel.getGoalCalories() != 0f) {
//            for (Listener listener : mListeners) listener.onProvideMealModel(mMealModel);
//        }
    }

    private void notifyFailure() {
//        for (Listener listener : mListeners) listener.onProvideMealModelFailed();
    }

    void fetchMealModelAndNotify(@NonNull String meal, int dailyGoalCalories) {
//        float mealGoalCalories;
//        switch (meal) {
//            case Constants.BREAKFAST:
//                mealGoalCalories = dailyGoalCalories * .3f;
//                break;
//            case Constants.DINNER:
//                mealGoalCalories = dailyGoalCalories * .4f;
//                break;
//            case Constants.SUPPER:
//                mealGoalCalories = dailyGoalCalories * .25f;
//                break;
//            case Constants.SNACKS:
//                mealGoalCalories = dailyGoalCalories * .05f;
//                break;
//            default:
//                throw new RuntimeException("arg meal should be one of the following Constants.BREAKFAST, Constants.DINNER, Constants.SUPPER, Constants.SNACKS");
//        }
//        mMealModel.setGoalCalories((int) mealGoalCalories);
//        fetchCurrentMealFoodsAndParseResult(meal);
    }

    private void fetchCurrentMealFoodsAndParseResult(@NonNull String meal) {
//        mFirebaseFirestore.collection(Constants.COLLECTION_USER_ADDED_FOOD_NUTRIENTS)
//                .whereEqualTo(Constants.UID, mUserId)
//                .whereEqualTo(Constants.MEAL, meal)
//                .whereEqualTo(Constants.DATE, Utils.getDate())
//                .get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    ArrayList<FirebaseFoodNutrients> firebaseFoodnutrients = new ArrayList<>();
//                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
//                        firebaseFoodnutrients.add(documentSnapshot.toObject(FirebaseFoodNutrients.class));
//                    }
//
//                    if (firebaseFoodnutrients.size() == 0) {
//                        for (Listener listener : mListeners) listener.noProductsAdded();
//                        return;
//                    }
//                    parseBaseNutritionAndProductModels(firebaseFoodnutrients);
//                    mNutritionInfoParser.parseNutritionInfoAndNotify(firebaseFoodnutrients);
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "onCancelled: ", e);
//                    notifyFailure();
//                });
    }

//    @Override
//    public void onNutritionInfoParsed(List<NutritionDetailListModel> nutritionDetails) {
//        mMealModel.setNutritionDetailListModels(nutritionDetails);
//        notifyControllerIAllFieldsPresent();
//    }

//    @Override
//    public void onNutritionInfoParseFailed() {
//        Log.e(TAG, "onNutritionInfoParseFailed: called");
//        notifyFailure();
//    }

    private void parseBaseNutritionAndProductModels(ArrayList<FirebaseFoodNutrients> firebaseFoodnutrients) {
//        List<ProductListModel> productListModels = new ArrayList<>();
//        float calories = 0, carbohydrates = 0, fats = 0, proteins = 0;
//
//        for (FirebaseFoodNutrients firebaseFoodnutrients : firebaseFoodnutrients) {
//            calories += firebaseFoodnutrients.totalCalories;
//            carbohydrates += firebaseFoodnutrients.totalCarbohydrates;
//            fats += firebaseFoodnutrients.totalFats;
//            proteins += firebaseFoodnutrients.totalProteins;
//
//            productListModels.add(new ProductListModel(
//                    firebaseFoodnutrients.foodName, firebaseFoodnutrients.currentServingQuantity,
//                    firebaseFoodnutrients.servingUnit, (int) firebaseFoodnutrients.totalCalories
//            ));
//        }
//
//        mMealModel.setBaseNutrition(new BaseNutrition(calories, carbohydrates, fats, proteins));
//        mMealModel.setProducts(productListModels);
//
//        notifyControllerIAllFieldsPresent();
    }
}
