package com.kumela.cmeter.network.api.search;

import android.util.Log;

import androidx.annotation.NonNull;

import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.model.search.SearchItem;
import com.kumela.cmeter.model.search.SearchResponse;
import com.kumela.cmeter.network.api.NutritionXService;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Toko on 23,June,2020
 **/

public class FetchSearchResultsUseCase extends BaseObservable<FetchSearchResultsUseCase.Listener> {

    private static final String TAG = "FetchSearchResultsUseCa";


    public interface Listener {

        void onSearchItemsFetched(Set<SearchItem> searchItems);

        void onSearchItemsFetchFailed();
    }

    private final NutritionXService mNutritionXService;

    public FetchSearchResultsUseCase(NutritionXService nutritionXService) {
        this.mNutritionXService = nutritionXService;
    }

    public void fetchSearchItemsAndNotify(String query) {
        Log.d(TAG, "fetchSearchItemsAndNotify: called query = " + query);

        mNutritionXService.getSearchResult(new SearchRequest(query)).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    SearchResponse searchResponse = response.body();
                    if (searchResponse != null) {
                        notifySuccess(searchResponse);
                    } else notifyFailure();
                } else notifyFailure();
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: error occurred", t);
                notifyFailure();
            }
        });
    }

    private void notifySuccess(@NonNull SearchResponse searchResponse) {
        Log.d(TAG, "notifySuccess: called, searchResponse = " + searchResponse);

        for (Listener listener : getListeners()) {
            listener.onSearchItemsFetched(searchResponse.searchItems);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onSearchItemsFetchFailed();
        }
    }
}
