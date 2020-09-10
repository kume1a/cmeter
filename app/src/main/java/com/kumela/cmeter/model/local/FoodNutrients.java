package com.kumela.cmeter.model.local;

import androidx.annotation.NonNull;

import com.kumela.cmeter.model.api.food.Nutrients;
import com.kumela.cmeter.model.api.nutrients.NutrientsResponseSchema;
import com.kumela.cmeter.model.api.nutrients.ParsedIngredients;
import com.kumela.cmeter.model.api.nutrients.TotalNutrient;
import com.kumela.cmeter.model.api.nutrients.TotalNutrients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Toko on 28,July,2020
 **/

public class FoodNutrients {
    public String uri;
    public float totalWeight;

    public String foodId;
    public String food;

    public float quantity;
    public float weight;
    public String measureUri;
    /*public String measure;

    public List<String> dietLabels;
    public List<String> healthLabels;
    public List<String> cautions;*/

    private Nutrients nutrients;

    public TotalNutrient cholesterol;

    private List<TotalNutrient> subCarbohydrates;
    private List<TotalNutrient> subFats;
    private List<TotalNutrient> minerals;
    private List<TotalNutrient> vitamins;

    @SuppressWarnings("unused")
    public FoodNutrients() {
    }

    public FoodNutrients(NutrientsResponseSchema nutrientsResponseSchema) {
        this.uri = nutrientsResponseSchema.uri;
        this.totalWeight = nutrientsResponseSchema.totalWeight;
        /*this.dietLabels = nutrientsResponseSchema.dietLabels;
        this.healthLabels = nutrientsResponseSchema.healthLabels;
        this.cautions = nutrientsResponseSchema.cautions;*/


        ParsedIngredients parsedIngredients = nutrientsResponseSchema.ingredients.get(0).parsed.get(0);

        this.foodId = parsedIngredients.foodId;
        this.food = parsedIngredients.food;
        this.quantity = parsedIngredients.quantity;
        this.weight = parsedIngredients.weight;
        this.measureUri = parsedIngredients.measureUri;
//        this.measure = getMeasureUnitFromUri(this.measureUri);


        TotalNutrients totalNutrients = nutrientsResponseSchema.totalNutrients;

        this.nutrients = new Nutrients(
                totalNutrients.energy.quantity / this.quantity,
                totalNutrients.carbohydrates.quantity / this.quantity,
                totalNutrients.fat.quantity / this.quantity,
                totalNutrients.protein.quantity / this.quantity,
                totalNutrients.fiber.quantity / this.quantity
        );

        this.cholesterol = totalNutrients.cholesterol;

        this.subCarbohydrates = new ArrayList<>(Arrays.asList(
                totalNutrients.sugars,
                totalNutrients.fiber
        ));
        this.subFats = new ArrayList<>(Arrays.asList(
                totalNutrients.saturated, totalNutrients.monounsaturated,
                totalNutrients.polyunsaturated, totalNutrients.trans
        ));
        this.minerals = new ArrayList<>(Arrays.asList(
                totalNutrients.calcium, totalNutrients.phosphorus,
                totalNutrients.potassium, totalNutrients.sodium,
                totalNutrients.magnesium, totalNutrients.iron
        ));
        this.vitamins = new ArrayList<>(Arrays.asList(
                totalNutrients.vitamin_a, totalNutrients.thiamin_b1,
                totalNutrients.riboflavin_b2, totalNutrients.niacin_b3,
                totalNutrients.vitamin_b6, totalNutrients.folate_equivalent,
                totalNutrients.vitamin_b12, totalNutrients.vitamin_c,
                totalNutrients.vitamin_d, totalNutrients.vitamin_e,
                totalNutrients.vitamin_k
        ));

        subCarbohydrates.removeAll(Collections.singleton(null));
        subFats.removeAll(Collections.singleton(null));
        minerals.removeAll(Collections.singleton(null));
        vitamins.removeAll(Collections.singleton(null));

        divideQuantityToMeasure(subCarbohydrates);
        divideQuantityToMeasure(subFats);
        divideQuantityToMeasure(minerals);
        divideQuantityToMeasure(vitamins);
    }

    public FoodNutrients(FoodNutrients foodNutrients) {
        this.uri = foodNutrients.uri;
        this.totalWeight = foodNutrients.totalWeight;

        this.foodId = foodNutrients.foodId;
        this.food = foodNutrients.food;

        this.quantity = foodNutrients.quantity;
        this.weight = foodNutrients.weight;
        this.measureUri = foodNutrients.measureUri;
//        this.measure = foodNutrients.measure;

        /*this.dietLabels = foodNutrients.dietLabels;
        this.healthLabels = foodNutrients.healthLabels;
        this.cautions = foodNutrients.cautions;*/

        this.nutrients = foodNutrients.nutrients;

        this.cholesterol = foodNutrients.cholesterol;

        this.subCarbohydrates = foodNutrients.subCarbohydrates;
        this.subFats = foodNutrients.subFats;
        this.minerals = foodNutrients.minerals;
        this.vitamins = foodNutrients.vitamins;
    }

    private void divideQuantityToMeasure(List<TotalNutrient> totalNutrients) {
        for (TotalNutrient totalNutrient : totalNutrients) {
            totalNutrient.quantity /= this.quantity;
        }
    }

    private List<TotalNutrient> getTotalNutrientList(List<TotalNutrient> totalNutrients) {
        List<TotalNutrient> list = new ArrayList<>();
        for (TotalNutrient totalNutrient : totalNutrients) {
            list.add(new TotalNutrient(
                    totalNutrient.label,
                    totalNutrient.quantity * quantity,
                    totalNutrient.unit
            ));
        }
        return list;
    }

    public void setServingQuantity(float servingQuantity) {
        this.quantity = servingQuantity;
    }

    public Nutrients getNutrients(boolean perQuantity) {
        if (perQuantity) {
            return nutrients;
        }
        return new Nutrients(
                nutrients.calories * quantity,
                nutrients.carbohydrates * quantity,
                nutrients.fats * quantity,
                nutrients.proteins * quantity,
                nutrients.fiber * quantity
        );
    }

    public List<TotalNutrient> getSubCarbohydrates(boolean perServing) {
        if (perServing) {
            return subCarbohydrates;
        }
        return getTotalNutrientList(subCarbohydrates);
    }

    public List<TotalNutrient> getSubFats(boolean perServing) {
        if (perServing) {
            return subFats;
        }
        return getTotalNutrientList(subFats);
    }

    public List<TotalNutrient> getMinerals(boolean perServing) {
        if (perServing) {
            return minerals;
        }
        return getTotalNutrientList(minerals);
    }

    public List<TotalNutrient> getVitamins(boolean perServing) {
        if (perServing) {
            return vitamins;
        }
        return getTotalNutrientList(vitamins);
    }

    public Nutrients getNutrients() { return nutrients; }

    @SuppressWarnings("unused")
    public List<TotalNutrient> getSubCarbohydrates() { return subCarbohydrates; }
    @SuppressWarnings("unused")
    public List<TotalNutrient> getSubFats() { return subFats; }
    @SuppressWarnings("unused")
    public List<TotalNutrient> getMinerals() { return minerals; }
    @SuppressWarnings("unused")
    public List<TotalNutrient> getVitamins() { return vitamins; }

    @NonNull
    @Override
    public String toString() {
        return "FoodNutrients{" + "\n" +
                "uri='" + uri + '\'' + "\n" +
                "totalWeight=" + totalWeight + "\n" +
                "foodId='" + foodId + '\'' + "\n" +
                "food='" + food + '\'' + "\n" +
                "quantity=" + quantity + "\n" +
                "weight=" + weight + "\n" +
                "measureUri='" + measureUri + '\'' + "\n" +
                /*"measure='" + measure + '\'' + "\n" +
                "dietLabels=" + dietLabels + "\n" +
                "healthLabels=" + healthLabels + "\n" +
                "cautions=" + cautions + "\n" +*/
                "nutrients=" + nutrients + "\n" +
                "cholesterol=" + cholesterol + "\n" +
                "subCarbohydrates=" + subCarbohydrates + "\n" +
                "subFats=" + subFats + "\n" +
                "minerals=" + minerals + "\n" +
                "vitamins=" + vitamins + "\n" +
                '}';
    }
}
