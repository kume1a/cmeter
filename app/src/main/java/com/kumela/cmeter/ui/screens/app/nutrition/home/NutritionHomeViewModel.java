package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.firebase.AddedFood;
import com.kumela.cmeter.model.firebase.User;
import com.kumela.cmeter.model.local.NutritionHomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toko on 15,July,2020
 **/

public class NutritionHomeViewModel extends ViewModel {

    private static final String TAG = "NutritionHomeViewModel";

    private FirebaseFirestore mFirebaseFirestore;
    private String mUserId;

    private int mGoalCaloriesInDay;
    private MutableLiveData<NutritionHomeModel> mNutritionHomeModelLiveData;

    private ListenerRegistration mProductsQueryListenerRegistration;

    public NutritionHomeViewModel(String uid, FirebaseFirestore firebaseFirestore) {
        this.mFirebaseFirestore = firebaseFirestore;
        this.mUserId = uid;

        this.mNutritionHomeModelLiveData = new MutableLiveData<>();
    }

    public LiveData<NutritionHomeModel> getNutritionHomeModelLiveData() {
        return mNutritionHomeModelLiveData;
    }

    public void fetchNutritionHomeInfo() {
        if (mNutritionHomeModelLiveData.getValue() == null) {
            fetchUserAndNotify();
        }
        Log.d(TAG, "fetchNutritionHomeInfo: called");
    }

    private void fetchUserAndNotify() {
        // fetch user from database and pass it to added foods.
        mFirebaseFirestore.collection(Constants.COLLECTION_USERS)
                .document(mUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    try {
                        User user = documentSnapshot.toObject(User.class);

                        if (user == null) {
                            mNutritionHomeModelLiveData.setValue(null);
                        } else onUserInfoFetched(user);
                    } catch (Exception e) {
                        Log.e(TAG, "fetchUserAndNotify: ", e);
                    }
                })
                .addOnFailureListener(e -> {
                    mNutritionHomeModelLiveData.setValue(null);
                    Log.e(getClass().getSimpleName(), "onCancelled: ", e);
                });
    }

    private void onUserInfoFetched(User user) {
        mGoalCaloriesInDay = user.bmr + user.dailyExtraCalories;

        mProductsQueryListenerRegistration = mFirebaseFirestore.collection(Constants.COLLECTION_PRODUCTS)
                .whereEqualTo(Constants.UID, mUserId)
                .whereEqualTo(Constants.DATE, Utils.getDate())
                .addSnapshotListener((value, error) -> {

                    Log.d(TAG, "onUserInfoFetched: value = " + value);

                    if (value == null || error != null) {
                        Log.e(TAG, "onCancelled: ", error);
                        mNutritionHomeModelLiveData.setValue(null);
                        return;
                    }

                    List<AddedFood> addedFoods = new ArrayList<>();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        addedFoods.add(snapshot.toObject(AddedFood.class));
                    }

                    mNutritionHomeModelLiveData.setValue(new NutritionHomeModel(addedFoods, user));
                });
    }

    public int getGoalCaloriesInDay() {
        return mGoalCaloriesInDay;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mProductsQueryListenerRegistration.remove();
    }
}
