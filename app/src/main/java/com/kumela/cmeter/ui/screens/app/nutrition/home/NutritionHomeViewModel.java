package com.kumela.cmeter.ui.screens.app.nutrition.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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

    private DatabaseReference mDatabaseReference;
    private String mUserId;

    private int mGoalCaloriesInDay;
    private MutableLiveData<NutritionHomeModel> mNutritionHomeModelLiveData;
    private boolean mAlreadyFetched = false;

    private ValueEventListener mAddedFoodsEventListener;
    private Query mAddedFoodsQuery;

    public NutritionHomeViewModel(String uid, FirebaseDatabase firebaseDatabase) {
        this.mDatabaseReference = firebaseDatabase.getReference();
        this.mUserId = uid;

        this.mNutritionHomeModelLiveData = new MutableLiveData<>();
    }

    public LiveData<NutritionHomeModel> getNutritionHomeModelLiveData() {
        return mNutritionHomeModelLiveData;
    }

    public void fetchNutritionHomeInfo() {
        if (!mAlreadyFetched) fetchUserAndNotify();
        Log.d(TAG, "fetchNutritionHomeInfo: called");
    }

    private void fetchUserAndNotify() {
        // fetch user from database and pass it to added foods.
        mDatabaseReference.child(Constants.CHILD_USERS)
                .orderByKey()
                .equalTo(mUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() > 1) {
                            Log.wtf(TAG, "WTF, userCount > 1");
                        }

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {
                                onUserInfoFetched(user);
                            } else {
                                Log.e(TAG, "onDataChange: user is null");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        mNutritionHomeModelLiveData.setValue(null);
                        Log.e(getClass().getSimpleName(), "onCancelled: ", error.toException());
                    }
                });
    }

    private void onUserInfoFetched(User user) {
        mGoalCaloriesInDay = user.bmr + user.dailyExtraCalories;

        mAddedFoodsQuery = mDatabaseReference.child(Constants.CHILD_PRODUCTS)
                .orderByChild(Constants.UID_DATE)
                .equalTo(mUserId + Utils.getDate());

        mAddedFoodsEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AddedFood> addedFoods = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    addedFoods.add(dataSnapshot.getValue(AddedFood.class));
                }

                mNutritionHomeModelLiveData.setValue(new NutritionHomeModel(addedFoods, user));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: ", error.toException());
                mNutritionHomeModelLiveData.setValue(null);
            }
        };

        mAddedFoodsQuery.addValueEventListener(mAddedFoodsEventListener);
        mAlreadyFetched = true;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (mAddedFoodsQuery != null) {
            mAddedFoodsQuery.removeEventListener(mAddedFoodsEventListener);
        }
    }

    public int getGoalCaloriesInDay() {
        return mGoalCaloriesInDay;
    }
}
