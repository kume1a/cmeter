package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.firebase.FirebaseUserAddedFood;
import com.kumela.cmeter.model.firebase.FirebaseUser;
import com.kumela.cmeter.model.local.fragment_models.NutritionHomeModel;
import com.kumela.cmeter.ui.common.mvc.observanble.ObservableViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toko on 15,July,2020
 **/

public class NutritionHomeViewModel extends ObservableViewModel<NutritionHomeViewModel.Listener> {

    public interface Listener {
        void onProvideNutritionHomeModel(NutritionHomeModel nutritionHomeModel);

        void onProvideNutritionHomeModelFailed();
    }

    private static final String TAG = "NutritionHomeViewModel";

    private FirebaseFirestore mFirebaseFirestore;
    private String mUserId;

    private int mGoalCaloriesInDay;

    private NutritionHomeModel mNutritionHomeModel;

    public NutritionHomeViewModel(String uid, FirebaseFirestore firebaseFirestore) {
        this.mFirebaseFirestore = firebaseFirestore;
        this.mUserId = uid;
    }

    public void fetchNutritionHomeInfo() {
        if (mNutritionHomeModel == null) {
            startNutritionHomeModelFetch();
        } else notifySuccess(mNutritionHomeModel);
    }

    private void notifySuccess(NutritionHomeModel nutritionHomeModel) {
        for (Listener listener : getListeners()) {
            listener.onProvideNutritionHomeModel(nutritionHomeModel);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onProvideNutritionHomeModelFailed();
        }
    }

    private void startNutritionHomeModelFetch() {
        // fetch user from database and pass it to added foods.
        mFirebaseFirestore.collection(Constants.COLLECTION_USERS)
                .document(mUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    try {
                        FirebaseUser firebaseUser = documentSnapshot.toObject(FirebaseUser.class);

                        if (firebaseUser == null) {
                            Log.e(TAG, "fetchUserAndNotify: user = null", new NullPointerException());
                            notifyFailure();
                        } else onUserInfoFetched(firebaseUser); // SUCCESS
                    } catch (Exception e) {
                        notifyFailure();
                        Log.e(TAG, "fetchUserAndNotify: ", e);
                    }
                })
                .addOnFailureListener(e -> {
                    notifyFailure();
                    Log.e(getClass().getSimpleName(), "onCancelled: ", e);
                });
    }

    private void onUserInfoFetched(FirebaseUser firebaseUser) {
        mGoalCaloriesInDay = firebaseUser.bmr + firebaseUser.dailyExtraCalories;

        // TODO: 8/4/2020 change implementation to only read and add food nutrients that changed
        //                docs from https://firebase.google.com/docs/firestore/query-data/listen
        ListenerRegistration listenerRegistration =
                mFirebaseFirestore.collection(Constants.COLLECTION_USER_ADDED_FOODS)
                        .whereEqualTo(FirebaseUserAddedFood.UID, mUserId)
                        .whereEqualTo(FirebaseUserAddedFood.DATE, Utils.getDate())
                        .addSnapshotListener((value, error) -> {
                            if (value == null || error != null) {
                                Log.e(TAG, "onCancelled: ", error);
                                notifyFailure();
                                return;
                            }
                            List<FirebaseUserAddedFood> firebaseUserAddedFoods = new ArrayList<>();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                firebaseUserAddedFoods.add(snapshot.toObject(FirebaseUserAddedFood.class));
                            }

                            Log.d(TAG, "onUserInfoFetched: firebaseAddedFoods " + firebaseUserAddedFoods);

                            mNutritionHomeModel = new NutritionHomeModel(firebaseUserAddedFoods, firebaseUser);
                            notifySuccess(mNutritionHomeModel);
                        });
        registerSnapshotListener(listenerRegistration);
    }

    public int getGoalCaloriesInDay() {
        return mGoalCaloriesInDay;
    }
}
