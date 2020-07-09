package com.kumela.cmeter.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailItem {
    private String name;
    private Float value;
    private String unit;
    private boolean isHeader;
    private boolean hasValue;

    public NutritionDetailItem(String name, Float value, String unit, boolean isHeader, boolean hasValue) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.isHeader = isHeader;
        this.hasValue = hasValue;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getValue() {
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public boolean hasValue() {
        return hasValue;
    }

    @NonNull
    @Override
    public String toString() {
        return "NutritionDetailsItem{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
