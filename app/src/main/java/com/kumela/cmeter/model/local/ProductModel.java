package com.kumela.cmeter.model.local;

/**
 * Created by Toko on 21,July,2020
 **/

public class ProductModel {
    public String foodName;

    public Float quantity;
    public String servingUnit;

    public Integer calories;

    public ProductModel(
            String foodName,
            Float quantity,
            String servingUnit,
            Integer calories
    ) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.servingUnit = servingUnit;
        this.calories = calories;
    }
}
