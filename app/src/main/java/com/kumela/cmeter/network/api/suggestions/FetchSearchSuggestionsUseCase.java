package com.kumela.cmeter.network.api.suggestions;

import android.util.Log;

import androidx.annotation.NonNull;

import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.common.Constants;
import com.kumela.cmeter.network.api.EdamamApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Toko on 01,August,2020
 **/

public class FetchSearchSuggestionsUseCase extends BaseObservable<FetchSearchSuggestionsUseCase.Listener> {

    private static final String TAG = "FetchSearchSuggestionsU";

    public interface Listener {
        void onSearchSuggestionsFetched(String[] suggestions);

        void onSearchSuggestionsFetchFailed();
    }

    private final EdamamApiService mEdamamApiService;

    public FetchSearchSuggestionsUseCase(EdamamApiService edamamApiService) {
        this.mEdamamApiService = edamamApiService;
    }

    public void fetchSearchSuggestionsAndNotify(String query, int limit) {
        mEdamamApiService.getSearchSuggestions(
                Constants.APP_ID, Constants.APP_KEY,
                query, limit).enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(@NonNull Call<String[]> call, @NonNull Response<String[]> response) {
                if (response.isSuccessful()) {
                    String[] suggestions = response.body();
                    if (suggestions != null) {
                        notifySuccess(suggestions);
                    } else notifyFailure();
                } else {
                    Log.e(TAG, "onResponse: response failure, " + response.toString());
                    notifyFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String[]> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: error occurred", t);
                notifyFailure();
            }
        });

    }

    private void notifySuccess(@NonNull String[] suggestions) {
        for (Listener listener : getListeners()) {
            listener.onSearchSuggestionsFetched(suggestions);
        }
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onSearchSuggestionsFetchFailed();
        }
    }
}
