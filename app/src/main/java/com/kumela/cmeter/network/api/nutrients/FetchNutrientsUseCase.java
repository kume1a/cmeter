package com.kumela.cmeter.network.api.nutrients;

import android.util.Log;

import androidx.annotation.NonNull;

import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.model.api.nutrients.NutrientsResponseSchema;
import com.kumela.cmeter.model.local.FoodNutrients;
import com.kumela.cmeter.network.api.EdamamApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Toko on 27,July,2020
 **/

public class FetchNutrientsUseCase extends BaseObservable<FetchNutrientsUseCase.Listener> {

    private static final String TAG = "FetchNutrientsUseCase";

    public interface Listener {

        void onNutritionFetched(FoodNutrients foodNutrients);

        void onNutritionFetchFailed();
    }

    private final EdamamApiService mEdamamApiService;

    public FetchNutrientsUseCase(EdamamApiService edamamApiService) {
        this.mEdamamApiService = edamamApiService;
    }

    public void fetchNutrientsAndNotify(String foodId, float quantity, String measureUri) {
        List<IngredientsRequestSchema> ingredients = new ArrayList<>(Collections.singletonList(
                new IngredientsRequestSchema(
                        quantity, measureUri, foodId
                )
        ));
        NutrientsRequestSchema requestSchema = new NutrientsRequestSchema(ingredients);

        Log.d(TAG, "fetchNutrientsAndNotify() called with: foodId = [" + foodId + "], quantity = [" + quantity + "], measureUri = [" + measureUri + "]");
        mEdamamApiService.getFoodNutrients(requestSchema)
                .enqueue(new Callback<NutrientsResponseSchema>() {
                    @Override
                    public void onResponse(@NonNull Call<NutrientsResponseSchema> call, @NonNull Response<NutrientsResponseSchema> response) {
                        if (response.isSuccessful()) {
                            NutrientsResponseSchema nutrientsResponseSchema = response.body();
                            if (nutrientsResponseSchema != null) {
                                notifySuccess(nutrientsResponseSchema);
                            } else notifyFailure();
                        } else {
                            Log.e(TAG, "onResponse: response failure, " + response.toString());
                            notifyFailure();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NutrientsResponseSchema> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: error occurred", t);
                        notifyFailure();
                    }
                });
    }

    private void notifySuccess(@NonNull NutrientsResponseSchema nutrientsResponseSchema) {
        for (Listener listener : getListeners()) {
            listener.onNutritionFetched(new FoodNutrients(nutrientsResponseSchema));
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onNutritionFetchFailed();
        }
    }
}
