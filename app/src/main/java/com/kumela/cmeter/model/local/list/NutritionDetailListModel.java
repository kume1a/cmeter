package com.kumela.cmeter.model.local.list;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailListModel {
    private final String name;
    private Float value;
    private final String unit;
    private final boolean isHeader;

    public NutritionDetailListModel(
            String name,
            Float value,
            String unit,
            boolean isHeader
    ) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.isHeader = isHeader;
    }

    public String getName() {
        return name;
    }

    public Float getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isHeader() {
        return isHeader;
    }

    @NonNull
    @Override
    public String toString() {
        return "NutritionDetailItem{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", isHeader=" + isHeader +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionDetailListModel that = (NutritionDetailListModel) o;
        return isHeader == that.isHeader &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, unit, isHeader);
    }
}
