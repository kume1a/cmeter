package com.kumela.cmeter.network.api.nutrition;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 21,June,2020
 **/

public class NutritionRequest {
    @SerializedName("query")
    public String query;

    @SerializedName("line_delimited")
    public boolean lineDelimited;

    @SerializedName("use_raw_foods")
    public boolean useRawFoods;

    @SerializedName("timezone")
    public String timeZone;

    @SerializedName("use_branded_foods")
    public boolean useBrandedFoods;

    public NutritionRequest(String query) {
        this.query = query;

        this.lineDelimited = false;
        this.useRawFoods = false;
        this.useBrandedFoods = false;
        this.timeZone = "Asia/Tbilisi";
    }
}
