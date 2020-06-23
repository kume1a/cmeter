package com.kumela.cmeter.network;

import androidx.annotation.NonNull;

/**
 * Created by Toko on 21,June,2020
 **/

public class SearchRequest {
    public String query;
    public boolean common;
    public boolean branded;

    public SearchRequest(String query, boolean common, boolean branded) {
        this.query = query;
        this.common = common;
        this.branded = branded;
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
