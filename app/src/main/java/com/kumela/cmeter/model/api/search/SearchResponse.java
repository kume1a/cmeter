package com.kumela.cmeter.model.api.search;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by Toko on 21,June,2020
 **/

public class SearchResponse {
    @SerializedName("common")
    public Set<SearchItem> searchItems;

//    @SerializedName("branded")
//    public List<BrandedItem> brandedItems;

    @NonNull
    @Override
    public String toString() {
        return "SearchResponse{" +
                "searchItems=" + searchItems +
                '}';
    }
}
