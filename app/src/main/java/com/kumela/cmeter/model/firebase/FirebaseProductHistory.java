package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Toko on 06,August,2020
 **/

public class FirebaseProductHistory {
    public static final String PRODUCT_HISTORY = "productHistory";

    public List<FirebaseProductHistoryItem> productHistory;

    @SuppressWarnings("unused")
    public FirebaseProductHistory() {
    }

    public FirebaseProductHistory(List<FirebaseProductHistoryItem> productHistory) {
        this.productHistory = productHistory;
    }

    @NonNull
    @Override
    public String toString() {
        return "FirebaseProductHistory{" +
                "productHistory=" + productHistory + '}';
    }
}
