package com.kumela.cmeter.network;

import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.model.search.SearchResponse;
import com.kumela.cmeter.model.nutrition.NutritionResponse;
import com.kumela.cmeter.network.nutrition.NutritionRequest;
import com.kumela.cmeter.network.search.SearchRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Toko on 20,June,2020
 **/

public interface NutritionXService {
    @Headers({
            "x-app-id: " + Constants.APP_ID,
            "x-app-key: " + Constants.API_KEY
    })
    @POST(Constants.URL_PATH_NATURAL_NUTRIENTS)
    Call<NutritionResponse> getNutritionInfo(@Body NutritionRequest nutritionRequest);


    @Headers({
            "x-app-id: " + Constants.APP_ID,
            "x-app-key: " + Constants.API_KEY
    })
    @POST(Constants.URL_PATH_SEARCH_INSTANT)
    Call<SearchResponse> getSearchResult(@Body SearchRequest searchRequest);
}
