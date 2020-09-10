package com.kumela.cmeter.model.firebase;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Toko on 01,August,2020
 **/

public class FirebaseSearchHistory {
    public static final String HISTORY = "history";

    public List<String> history;

    @SuppressWarnings("unused")
    public FirebaseSearchHistory() {
    }

    public FirebaseSearchHistory(List<String> history) {
        this.history = history;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchHistory{" +
                "history=" + history +
                '}';
    }
}
