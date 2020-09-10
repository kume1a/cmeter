package com.kumela.cmeter.network.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.firebase.FirebaseFoodNutrients;
import com.kumela.cmeter.model.firebase.FirebaseProduct;
import com.kumela.cmeter.model.firebase.FirebaseProductHistory;
import com.kumela.cmeter.model.firebase.FirebaseProductHistoryItem;
import com.kumela.cmeter.model.firebase.FirebaseUserAddedFood;
import com.kumela.cmeter.model.local.FoodNutrients;
import com.kumela.cmeter.network.api.nutrients.FetchNutrientsUseCase;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Toko on 13,July,2020
 **/

public class FirebaseProductManager extends BaseObservable<FirebaseProductManager.Listener> {

    private static final String TAG = "FirebaseProductManager";

    public interface Listener {
        void onAddFoodSucceeded();

        void onAddFoodFailed();
    }

    private String mUserId;
    private FirebaseFirestore mFirestore;
    private FetchNutrientsUseCase mFetchNutrientsUseCase;

    @Inject
    public FirebaseProductManager(String uid,
                                  FirebaseFirestore firebaseFirestore,
                                  FetchNutrientsUseCase fetchNutrientsUseCase) {
        this.mUserId = uid;
        this.mFirestore = firebaseFirestore;
        this.mFetchNutrientsUseCase = fetchNutrientsUseCase;
    }

    public void writeProductAndNotify(String foodId,
                                      float quantity,
                                      List<Measure> measures,
                                      String measureUri,
                                      String meal, boolean favorite) {
        new Thread(new FirebaseFoodNutrientsWriter(
                getListeners(), mFirestore, mFetchNutrientsUseCase, mUserId,
                foodId, quantity, measures, measureUri, meal, favorite
        )).start();
    }

    private static class FirebaseFoodNutrientsWriter
            implements Runnable, FetchNutrientsUseCase.Listener {

        private final Object mLock = new Object();

        // class parameters
        private Set<Listener> mListeners;
        private FirebaseFirestore mFirestore;
        private FetchNutrientsUseCase mFetchNutrientsUseCase;
        private String mUserId;
        private final String mFoodId;
        private final float mQuantity;
        private final List<Measure> mMeasures;
        private final String mMeasureUri;
        private final String mMeasureLabel;
        private final String mMeal;
        private final boolean mFavorite;

        // cached fields
        private WriteBatch mWriteBatch;
        private DocumentReference mReference;
        private int measuresRequested = 0;

        private FirebaseFoodNutrientsWriter(Set<Listener> listeners,
                                            FirebaseFirestore firestore,
                                            FetchNutrientsUseCase fetchNutrientsUseCase,
                                            String userId,
                                            String foodId,
                                            float quantity,
                                            List<Measure> measures,
                                            String measureUri,
                                            String meal,
                                            boolean favorite) {
            mListeners = listeners;
            mFirestore = firestore;
            mFetchNutrientsUseCase = fetchNutrientsUseCase;
            mUserId = userId;
            mFoodId = foodId;
            mQuantity = quantity;
            mMeasures = measures;
            mMeasureUri = measureUri;
            mMeasureLabel = getMeasure(measureUri).label;
            mMeal = meal;
            mFavorite = favorite;

            mWriteBatch = mFirestore.batch();
            mFetchNutrientsUseCase.registerListener(this);
        }

        private void notifyFailure(Exception e) {
            Log.e(TAG, "notifyFailure: ", e);
            for (Listener listener : mListeners) {
                listener.onAddFoodFailed();
            }
        }

        private void notifySuccess(Void v) {
            for (Listener listener : mListeners) {
                listener.onAddFoodSucceeded();
            }
        }

        @Override
        public void run() {
            CollectionReference collectionReference = mFirestore.collection(Constants.COLLECTION_PRODUCTS);

            collectionReference
                    .whereEqualTo(FirebaseFoodNutrients.FOOD_ID, mFoodId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Log.d(TAG, "run: getting product from firebase");
                            writeUserAddedFood(queryDocumentSnapshots);
                        } else createFirebaseFoodNutrientsDocument(collectionReference);
                    })
                    .addOnFailureListener(this::notifyFailure);
        }

        private void createFirebaseFoodNutrientsDocument(CollectionReference collectionReference) {
            mReference = collectionReference.document();
            Log.d(TAG, "createFirebaseFoodNutrientsDocument: creating new FirebaseFoodNutrientsDocument");

            mReference.set(new FirebaseFoodNutrients(mFoodId))
                    .addOnSuccessListener(aVoid -> writeProductToDatabase())
                    .addOnFailureListener(this::notifyFailure);
        }

        private void writeProductToDatabase() {
            for (Measure measure : mMeasures) {
                mFetchNutrientsUseCase.fetchNutrientsAndNotify(
                        mFoodId, Utils.getQuantity(measure.uri), measure.uri);
            }
        }

        @Override
        public void onNutritionFetched(FoodNutrients foodNutrients) {
            Measure measure = getMeasure(foodNutrients.measureUri);

            if (mMeasureUri.equals(measure.uri)) {
                writeUserAddedFood(foodNutrients, false);
            }

            Log.d(TAG, "onNutritionFetched: writing uri = " + foodNutrients.measureUri);
            DocumentReference documentReference = mReference
                    .collection(Constants.COLLECTION_PRODUCT_MEASURES)
                    .document();
            synchronized (mLock) {
                mWriteBatch.set(documentReference, new FirebaseProduct(measure, foodNutrients));
                measuresRequested++;
            }

            if (measuresRequested == mMeasures.size()) {
                executeBatch();
            }
        }

        @NonNull
        private Measure getMeasure(String measureUri) {
            for (Measure measure : mMeasures) {
                if (measure.uri.equals(measureUri)) {
                    return measure;
                }
            }
            throw new RuntimeException("Error measure uri not in measures list");
        }

        private void executeBatch() {
            synchronized (mLock) {
                mWriteBatch.commit()
                        .addOnSuccessListener(this::notifySuccess)
                        .addOnFailureListener(this::notifyFailure);
            }
        }

        private void writeUserAddedFood(FoodNutrients foodNutrients, boolean executeBatch) {
            // get reference to user added foods
            DocumentReference userAddedFoodRef = mFirestore
                    .collection(Constants.COLLECTION_USER_ADDED_FOODS)
                    .document();

            // get reference to product history
            DocumentReference historyRef = mFirestore
                    .collection(Constants.COLLECTION_PRODUCT_HISTORY)
                    .document(mUserId);

            // TODO: 8/5/2020 cache measure label to member variable
            FirebaseProductHistoryItem firebaseProductHistoryItem = new FirebaseProductHistoryItem(
                    mFoodId, foodNutrients.food, foodNutrients.quantity,
                    mMeasureLabel, foodNutrients.getNutrients().calories,
                    mFavorite, Utils.getDate()
            );

            // create copy to not override original food nutrients object and
            // change quantity from default to user specified one
            FoodNutrients foodNutrientsCopy = new FoodNutrients(foodNutrients);
            foodNutrientsCopy.setServingQuantity(mQuantity);
            FirebaseUserAddedFood firebaseUserAddedFood = new FirebaseUserAddedFood(
                    foodNutrientsCopy, mMeasures, mUserId, mFoodId,
                    mMeal, mMeasureUri, mFavorite, Utils.getDate()
            );

            synchronized (mLock) {
                mWriteBatch.update(historyRef, FirebaseProductHistory.PRODUCT_HISTORY, FieldValue.arrayUnion(firebaseProductHistoryItem));
                mWriteBatch.set(userAddedFoodRef, firebaseUserAddedFood);
            }

            if (executeBatch) executeBatch();
        }

        private void writeUserAddedFood(QuerySnapshot queryDocumentSnapshots) {
            queryDocumentSnapshots
                    .getDocuments()
                    .get(0)
                    .getReference()
                    .collection(Constants.COLLECTION_PRODUCT_MEASURES)
                    .whereEqualTo(FirebaseProduct.MEASURE_URI, mMeasureUri)
                    .get()
                    .addOnSuccessListener(documentSnapshots -> {
                        @SuppressWarnings("ConstantConditions")
                        FoodNutrients foodNutrients = documentSnapshots
                                .getDocuments()
                                .get(0)
                                .toObject(FirebaseProduct.class)
                                .foodNutrients;
                        writeUserAddedFood(foodNutrients, true);
                    })
                    .addOnFailureListener(this::notifyFailure);
        }

        @Override
        public void onNutritionFetchFailed() {
            notifyFailure(new RuntimeException());
        }
    }
}
