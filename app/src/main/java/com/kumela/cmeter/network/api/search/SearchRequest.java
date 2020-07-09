package com.kumela.cmeter.network.api.search;

import androidx.annotation.NonNull;

/**
 * Created by Toko on 21,June,2020
 **/

public class SearchRequest {
    public String query;
    public boolean common;
    public boolean branded;

    public SearchRequest(String query) {
        this.query = query;
        this.common = true;
        this.branded = false;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchRequest{" +
                "query='" + query + '\'' +
                ", common=" + common +
                ", branded=" + branded +
                '}';
    }
}
