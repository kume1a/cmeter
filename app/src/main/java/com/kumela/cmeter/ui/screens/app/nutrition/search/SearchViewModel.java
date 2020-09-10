package com.kumela.cmeter.ui.screens.app.nutrition.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kumela.cmeter.model.api.food.Hint;
import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.local.list.FoodListModel;
import com.kumela.cmeter.network.api.food.FetchFoodUseCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Toko on 01,July,2020
 **/

public class SearchViewModel extends ViewModel implements FetchFoodUseCase.Listener {

    private static final String TAG = "SearchViewModel";

    private MutableLiveData<List<FoodListModel>> mSearchItems;

    LiveData<List<FoodListModel>> getSearchItemsLiveData() {
        return mSearchItems;
    }

    private final FetchFoodUseCase mFetchFoodUseCase;

    public SearchViewModel(FetchFoodUseCase fetchFoodUseCase) {
        this.mFetchFoodUseCase = fetchFoodUseCase;

        mSearchItems = new MutableLiveData<>();

        mFetchFoodUseCase.registerListener(this);
    }

    void fetchSearchResultsAndNotify(@NonNull String query) {
        if (mSearchItems.getValue() == null) {
            mFetchFoodUseCase.fetchFoodAndNotify(query);
        }
    }

    @Override
    public void onFoodFetched(List<Hint> hints) {
        List<FoodListModel> foodListModels = new ArrayList<>();
        for (Hint hint : hints) {
            Measure[] measures = new Measure[hint.measures.size()];
            hint.measures.toArray(measures);
            foodListModels.add(new FoodListModel(
                    hint.food.foodId,
                    hint.food.label,
                    hint.food.nutrients.calories,
                    measures
            ));
        }

        // filtering list for unique names
        List<String> uniqueNames = new ArrayList<>(foodListModels.size());
        FoodListModel currentItem;
        Iterator<FoodListModel> iterator = foodListModels.iterator();
        while (iterator.hasNext()) {
            currentItem = iterator.next();
            if (uniqueNames.contains(currentItem.label.toLowerCase())) {
                iterator.remove();
            } else uniqueNames.add(currentItem.label.toLowerCase());
        }

        mSearchItems.setValue(foodListModels);
    }

    @Override
    public void onFoodFetchFailed() {
        Log.d(TAG, "onFoodFetchFailed: called");
        mSearchItems.setValue(null);
    }

    @Override
    protected void onCleared() {
        mFetchFoodUseCase.unregisterListener(this);
        super.onCleared();
    }
}
