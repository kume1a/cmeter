package com.kumela.cmeter.network.api;

import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.api.food.FoodResponseSchema;
import com.kumela.cmeter.model.api.nutrients.NutrientsResponseSchema;
import com.kumela.cmeter.network.api.nutrients.NutrientsRequestSchema;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Toko on 27,July,2020
 **/

public interface EdamamApiService {
    @GET(Constants.API_PATH_FOOD_DATABASE)
    Call<FoodResponseSchema> getFromFoodDatabase(@Query("app_id") String app_id,
                                                 @Query("app_key") String app_key,
                                                 @Query("ingr") String ingredients,
                                                 @Query("upc") String upc,
                                                 @Query("nutrition-type") String nutritionType,
                                                 @Query("health") String health,
                                                 @Query("calories") String calorieRange,
                                                 @Query("category") String category,
                                                 @Query("categoryLabel") String categoryLabel);


    @POST(Constants.API_PATH_NUTRIENTS + "?" + "app_id=" + Constants.APP_ID + "&app_key=" + Constants.APP_KEY)
    Call<NutrientsResponseSchema> getFoodNutrients(@Body NutrientsRequestSchema requestSchema);

    @GET(Constants.API_PATH_SUGGESTIONS)
    Call<String[]> getSearchSuggestions(@Query("app_id") String app_id,
                                        @Query("app_key") String app_key,
                                        @Query("q") String query,
                                        @Query("limit") int limit);
}
