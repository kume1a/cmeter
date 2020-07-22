package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.kumela.cmeter.model.api.nutrition.AltMeasure;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.local.NutritionDetailItem;
import com.kumela.cmeter.network.api.nutrition.FetchNutritionInfoUseCase;
import com.kumela.cmeter.network.firebase.FirebaseProductManager;
import com.kumela.cmeter.ui.common.util.NutritionInfoParser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsViewModel extends ViewModel
        implements FetchNutritionInfoUseCase.Listener, NutritionInfoParser.Listener, FirebaseProductManager.Listener {

    private static final String TAG = "FoodDetailsViewModel";

    interface Listener {
        void onProvideNutritionDetails(List<NutritionDetailItem> nutritionDetails);

        void onProvideNutritionInfo(NutritionInfo nutritionInfo);

        void onProvideNutritionInfoFailed();

        void onNutritionInfoUpdated(NutritionInfo nutritionInfo);

        void onNutritionDetailsUpdated(List<NutritionDetailItem> nutritionDetails);

        void onWriteProductCompleted();
    }

    private Set<Listener> mListeners = new HashSet<>(1);

    private List<NutritionDetailItem> mNutritionDetails;

    private NutritionInfo mNutritionInfo;

    private final FetchNutritionInfoUseCase mFetchNutritionInfoUseCase;
    private final NutritionInfoParser mNutritionInfoParser;
    private final FirebaseProductManager mFirebaseProductManager;

    public NutritionDetailsViewModel(FetchNutritionInfoUseCase fetchSearchResultsUseCase,
                                     NutritionInfoParser parser,
                                     FirebaseProductManager firebaseProductManager) {
        this.mFetchNutritionInfoUseCase = fetchSearchResultsUseCase;
        this.mNutritionInfoParser = parser;
        this.mFirebaseProductManager = firebaseProductManager;

        mFetchNutritionInfoUseCase.registerListener(this);
        mNutritionInfoParser.registerListener(this);
        mFirebaseProductManager.registerListener(this);
    }

    void fetchNutritionInfoAndNotify(String foodName) {
        if (mNutritionDetails == null) {
            Log.d(TAG, "fetchNutritionInfoAndNotify: fetching from use case");
            mFetchNutritionInfoUseCase.fetchNutritionInfoAndNotify(foodName);
        } else {
            Log.d(TAG, "fetchNutritionInfoAndNotify: providing from view model");
            for (Listener listener : mListeners) listener.onProvideNutritionInfo(mNutritionInfo);
            onNutritionInfoParsed(mNutritionDetails);
        }
    }

    @Override
    public void onNutritionInfoFetched(NutritionInfo nutritionInfo) {
        mNutritionInfo = nutritionInfo;
        for (Listener listener: mListeners) listener.onProvideNutritionInfo(nutritionInfo);

        mNutritionInfoParser.parseNutritionInfoAndNotify(nutritionInfo);
    }

    @Override
    public void onNutritionInfoFetchFailed() {
        for (Listener listener : mListeners) {
            listener.onProvideNutritionInfoFailed();
        }
    }

    void writeProduct(String mealType) {
        mFirebaseProductManager.writeProductAndNotify(mNutritionInfo, mealType);
    }

    @Override
    public void onDatabaseWriteCompleted() {
        for (Listener listener: mListeners) listener.onWriteProductCompleted();
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
        mFirebaseProductManager.unregisterListener(this);
    }

    @Override
    public void onNutritionInfoParsed(List<NutritionDetailItem> nutritionDetails) {
        if (mNutritionDetails == null) {
            for (Listener listener : mListeners) {
                listener.onProvideNutritionDetails(nutritionDetails);
            }
        } else {
            for (Listener listener: mListeners) {
                listener.onNutritionDetailsUpdated(nutritionDetails);
            }
        }
        mNutritionDetails = nutritionDetails;
    }

    @Override
    public void onNutritionInfoParseFailed() {
        for (Listener listener : mListeners) {
            listener.onProvideNutritionInfoFailed();
        }
    }

    // Serving unit and serving quantity changer functions
    public void setAltMeasure(AltMeasure altMeasure) {
        boolean valuesChanged = this.mNutritionInfo.setAltMeasure(altMeasure);

        if (valuesChanged) {
            for (Listener listener : mListeners) listener.onNutritionInfoUpdated(mNutritionInfo);
            mNutritionInfoParser.parseNutritionInfoAndNotify(mNutritionInfo);
        }
    }

    public void setQuantity(float servingQuantity) {
        this.mNutritionInfo.setCurrentServingQuantity(servingQuantity);

        for (Listener listener : mListeners) listener.onNutritionInfoUpdated(mNutritionInfo);
        mNutritionInfoParser.parseNutritionInfoAndNotify(mNutritionInfo);
    }
}
