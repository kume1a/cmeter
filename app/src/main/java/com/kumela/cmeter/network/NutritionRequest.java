package com.kumela.cmeter.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toko on 21,June,2020
 **/

public class NutritionRequest {
    @SerializedName("query")
    public String query;

    @SerializedName("num_servings")
    public int servings;

    @SerializedName("line_delimited")
    public boolean lineDelimited;

    @SerializedName("use_raw_foods")
    public boolean useRawFoods;

    @SerializedName("timezone")
    public String timeZone;

    @SerializedName("consumed_at")
    public String consumedAt;

    @SerializedName("use_branded_foods")
    public boolean useBrandedFoods;

    public NutritionRequest(
            String query,
            int servings,
            boolean lineDelimited,
            boolean useRawFoods,
            String timeZone,
            String consumedAt,
            boolean useBrandedFoods
    ) {
        this.query = query;
        this.servings = servings;
        this.lineDelimited = lineDelimited;
        this.useRawFoods = useRawFoods;
        this.timeZone = timeZone;
        this.consumedAt = consumedAt;
        this.useBrandedFoods = useBrandedFoods;
    }
}
