package com.kumela.cmeter.model.local;

import java.util.Objects;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailItem {
    private final String name;
    private final Float value;
    private final String unit;
    private final boolean isHeader;
    private final boolean hasValue;

    public NutritionDetailItem(
            String name,
            Float value,
            String unit,
            boolean isHeader,
            boolean hasValue
    ) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.isHeader = isHeader;
        this.hasValue = hasValue;
    }

    public String getName() { return name; }
    public Float getValue() { return value; }
    public String getUnit() { return unit; }
    public boolean isHeader() { return isHeader; }
    public boolean hasValue() { return hasValue; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionDetailItem that = (NutritionDetailItem) o;
        return isHeader == that.isHeader &&
                hasValue == that.hasValue &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, unit, isHeader, hasValue);
    }
}
