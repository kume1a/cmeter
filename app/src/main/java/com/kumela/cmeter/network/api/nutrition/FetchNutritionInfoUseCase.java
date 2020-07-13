package com.kumela.cmeter.network.api.nutrition;

import android.util.Log;

import androidx.annotation.NonNull;

import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.api.nutrition.NutritionResponse;
import com.kumela.cmeter.network.api.NutritionXService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Toko on 03,July,2020
 **/

public class FetchNutritionInfoUseCase extends BaseObservable<FetchNutritionInfoUseCase.Listener> {

    private static final String TAG = "FetchNutritionInfoUseCa";

    public interface Listener {

        void onNutritionInfoFetched(NutritionInfo nutritionInfo);

        void onNutritionInfoFetchFailed();

    }

    private final NutritionXService mNutritionXService;

    public FetchNutritionInfoUseCase(NutritionXService nutritionXService) {
        this.mNutritionXService = nutritionXService;
    }

    public void fetchNutritionInfoAndNotify(String foodName) {
        Log.d(TAG, "fetchSearchItemsAndNotify: called foodName = " + foodName);

        mNutritionXService.getNutritionInfo(new NutritionRequest(foodName)).enqueue(new Callback<NutritionResponse>() {
            @Override
            public void onResponse(@NonNull Call<NutritionResponse> call, @NonNull Response<NutritionResponse> response) {
                if (response.isSuccessful()) {
                    NutritionResponse nutritionResponse = response.body();
                    if (nutritionResponse != null) {
                        notifySuccess(nutritionResponse);
                    } else notifyFailure();
                } else notifyFailure();
            }

            @Override
            public void onFailure(@NonNull Call<NutritionResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: error occurred", t);
                notifyFailure();
            }
        });
    }

    private void notifySuccess(@NonNull NutritionResponse nutritionResponse) {
        Log.d(TAG, "notifySuccess: called, nutritionresponse = " + nutritionResponse);

        for (Listener listener : getListeners()) {
            listener.onNutritionInfoFetched(nutritionResponse.mNutritionInfos.get(0));
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onNutritionInfoFetchFailed();
        }
    }
}
