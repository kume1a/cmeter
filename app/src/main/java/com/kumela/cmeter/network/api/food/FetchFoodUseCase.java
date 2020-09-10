package com.kumela.cmeter.network.api.food;

import android.util.Log;

import androidx.annotation.NonNull;

import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.api.food.FoodResponseSchema;
import com.kumela.cmeter.model.api.food.Hint;
import com.kumela.cmeter.network.api.EdamamApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Toko on 03,July,2020
 **/

public class FetchFoodUseCase extends BaseObservable<FetchFoodUseCase.Listener> {

    private static final String TAG = "FetchFoodUseCase";

    public interface Listener {

        void onFoodFetched(List<Hint> hints);

        void onFoodFetchFailed();
    }

    private final EdamamApiService mEdamamApiService;

    public FetchFoodUseCase(EdamamApiService edamamApiService) {
        this.mEdamamApiService = edamamApiService;
    }

    public void fetchFoodAndNotify(String query) {
        mEdamamApiService.getFromFoodDatabase(
                Constants.APP_ID,
                Constants.APP_KEY,
                query,
                null, "logging", null, null, null, null
        ).enqueue(new Callback<FoodResponseSchema>() {
            @Override
            public void onResponse(@NonNull Call<FoodResponseSchema> call,@NonNull Response<FoodResponseSchema> response) {
                if (response.isSuccessful()) {
                    FoodResponseSchema foodResponseSchema = response.body();
                    if (foodResponseSchema != null) {
                        notifySuccess(foodResponseSchema);
                    } else notifyFailure();
                } else notifyFailure();
            }

            @Override
            public void onFailure(@NonNull Call<FoodResponseSchema> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: error occurred", t);
                notifyFailure();
            }
        });
    }

    private void notifySuccess(@NonNull FoodResponseSchema foodResponseSchema) {
        for (Listener listener : getListeners()) {
            listener.onFoodFetched(foodResponseSchema.hints);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onFoodFetchFailed();
        }
    }
}
