package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.list.BaseNutrition;
import com.kumela.cmeter.model.list.NutritionDetailItem;
import com.kumela.cmeter.model.api.Photo;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.network.api.nutrition.FetchNutritionInfoUseCase;
import com.kumela.cmeter.ui.common.util.NutritionInfoParser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsViewModel extends ViewModel
        implements FetchNutritionInfoUseCase.Listener, NutritionInfoParser.Listener {

    private static final String TAG = "FoodDetailsViewModel";

    interface Listener {
        void onProvideNutritionInfo(List<NutritionDetailItem> nutritionDetails);

        void onProvideNutritionInfoFailed();
    }

    private Set<Listener> mListeners = new HashSet<>(1);
    private List<NutritionDetailItem> mNutritionDetails;

    private MutableLiveData<Photo> mPhotoLiveData;
    private MutableLiveData<BaseNutrition> mBaseNutritionLiveData;

    private final FetchNutritionInfoUseCase mFetchNutritionInfoUseCase;
    private final NutritionInfoParser mNutritionInfoParser;

    public NutritionDetailsViewModel(FetchNutritionInfoUseCase fetchSearchResultsUseCase, NutritionInfoParser parser) {
        this.mFetchNutritionInfoUseCase = fetchSearchResultsUseCase;
        this.mNutritionInfoParser = parser;

        this.mPhotoLiveData = new MutableLiveData<>();
        this.mBaseNutritionLiveData = new MutableLiveData<>();

        mFetchNutritionInfoUseCase.registerListener(this);
        mNutritionInfoParser.registerListener(this);
    }

    void fetchNutritionInfoAndNotify(String foodName) {
        Log.d(TAG, "fetchNutritionInfoAndNotify: called foodName = " + foodName);
        if (mNutritionDetails == null) {
            Log.d(TAG, "fetchNutritionInfoAndNotify: fetching from use case");
            mFetchNutritionInfoUseCase.fetchNutritionInfoAndNotify(foodName);
        } else {
            Log.d(TAG, "fetchNutritionInfoAndNotify: providing from view model");
            onNutritionInfoParsed(mNutritionDetails);
        }
    }

    @Override
    public void onNutritionInfoFetched(NutritionInfo nutritionInfo) {
        mNutritionInfoParser.parseNutritionInfoAndNotify(nutritionInfo);

        mPhotoLiveData.setValue(nutritionInfo.photo);
        mBaseNutritionLiveData.setValue(new BaseNutrition(
                Utils.format(nutritionInfo.totalCalories),
                Utils.format(nutritionInfo.totalCarbohydrates),
                Utils.format(nutritionInfo.totalFat),
                Utils.format(nutritionInfo.totalProtein)
        ));
    }

    @Override
    public void onNutritionInfoFetchFailed() {
        for (Listener listener : mListeners) {
            listener.onProvideNutritionInfoFailed();
        }
    }

    void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mFetchNutritionInfoUseCase.unregisterListener(this);
        mNutritionInfoParser.unregisterListener(this);
    }

    @Override
    public void onNutritionInfoParsed(List<NutritionDetailItem> nutritionDetails) {
        mNutritionDetails = nutritionDetails;
        for (Listener listener : mListeners) {
            listener.onProvideNutritionInfo(nutritionDetails);
        }
    }

    @Override
    public void onNutritionInfoParseFailed() {
        for (Listener listener : mListeners) {
            listener.onProvideNutritionInfoFailed();
        }
    }

    LiveData<BaseNutrition> getBaseNutritionLiveData() {
        return mBaseNutritionLiveData;
    }

    LiveData<Photo> getPhotoLicaData() {
        return mPhotoLiveData;
    }
}
