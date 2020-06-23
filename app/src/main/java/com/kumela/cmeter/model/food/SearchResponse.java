package com.kumela.cmeter.model.food;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Toko on 21,June,2020
 **/

public class SearchResponse {
    @SerializedName("common")
    public List<SearchItem> searchItems;

//    @SerializedName("branded")
//    public List<BrandedItem> brandedItems;


    @Override
    public String toString() {
        return "SearchResponse{" +
                "searchItems=" + searchItems +
                '}';
    }
}
