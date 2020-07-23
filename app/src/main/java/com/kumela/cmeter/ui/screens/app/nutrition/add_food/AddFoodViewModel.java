package com.kumela.cmeter.ui.screens.app.nutrition.add_food;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.api.search.SearchItem;
import com.kumela.cmeter.model.firebase.AddedFood;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toko on 22,July,2020
 **/

public class AddFoodViewModel extends ViewModel {

    private static final String TAG = "AddFoodViewModel";

    private MutableLiveData<List<SearchItem>> mRecentLiveData;
    private MutableLiveData<List<SearchItem>> mFavoritesLiveData;

    public LiveData<List<SearchItem>> getRecentLiveData() {
        return mRecentLiveData;
    }

    public LiveData<List<SearchItem>> getFavoritesLiveData() {
        return mFavoritesLiveData;
    }

    private final String mUserId;
    private final DatabaseReference mDatabaseReference;

    public AddFoodViewModel(String userId, FirebaseDatabase firebaseDatabase) {
        mUserId = userId;
        mDatabaseReference = firebaseDatabase.getReference();

        mRecentLiveData = new MutableLiveData<>();
        mFavoritesLiveData = new MutableLiveData<>();
    }

    public void fetchRecentlyAddedProducts() {
        mDatabaseReference.child(Constants.CHILD_PRODUCTS)
                .orderByChild(Constants.UID)
                .limitToFirst(15)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<SearchItem> recentlyAdded = getSearchItemsFromSnapshot(snapshot);
                        mRecentLiveData.setValue(recentlyAdded);
                        Log.d(TAG, "onDataChange: called, fetching from firebase");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: ", error.toException());
                        mRecentLiveData.setValue(null);
                    }
                });
    }

    public void fetchFavorites() {
        mDatabaseReference.child(Constants.CHILD_PRODUCTS)
                .orderByChild(Constants.UID_FAVORITE)
                .equalTo(mUserId + true)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<SearchItem> recentlyAdded = getSearchItemsFromSnapshot(snapshot);
                        mRecentLiveData.setValue(recentlyAdded);
                        Log.d(TAG, "onDataChange: called, fetching from firebase");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: ", error.toException());
                        mRecentLiveData.setValue(null);
                    }
                });
    }

    @SuppressWarnings("ConstantConditions")
    private List<SearchItem> getSearchItemsFromSnapshot(DataSnapshot dataSnapshot) {
        List<SearchItem> list = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            AddedFood nutritionInfo = snapshot.getValue(AddedFood.class);
            list.add(new SearchItem(
                    nutritionInfo.foodName,
                    nutritionInfo.servingUnit,
                    nutritionInfo.currentServingQuantity
            ));
        }
        return list;
    }
}
