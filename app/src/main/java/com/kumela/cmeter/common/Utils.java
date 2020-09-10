package com.kumela.cmeter.common;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by Toko on 03,July,2020
 **/

public class Utils {
    public static String format(float f) {
        String s = Float.toString(f);
        return s.contains(".") ? s.replaceAll("0*$", "").replaceAll("\\.$", "") : s;
    }

    /**
     * return current formatted date from argument
     *
     * @param timeInMillis unix epoch time
     * @return string containing formatted value based on {@link com.kumela.cmeter.common.Constants}
     * DATE_FORMAT_PATTERN value
     */
    public static String getDate(@Nullable Long timeInMillis) {
        if (timeInMillis == null) {
            timeInMillis = System.currentTimeMillis();
        }

        Date date = new Date(timeInMillis);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_PATTERN, Locale.getDefault());
        return sdf.format(date);
    }

    public static String getDate() {
        return getDate(System.currentTimeMillis());
    }

    private static final Map<String, Integer> MEAL_TYPE_TO_STRING = new HashMap<String, Integer>() {{
        put(Constants.BREAKFAST, R.string.breakfast);
        put(Constants.DINNER, R.string.dinner);
        put(Constants.SUPPER, R.string.supper);
        put(Constants.SNACKS, R.string.snacks);
    }};

    @SuppressWarnings("ConstantConditions")
    public static String getMealTitle(Context context, String meal) {
        return context.getResources().getString(MEAL_TYPE_TO_STRING.get(meal));
    }

    public static float getQuantity(@NonNull String measureUri) {
        float quantity = 1f;
        switch (measureUri) {
            case "http://www.edamam.com/ontologies/edamam.owl#Measure_gram":
                quantity = 100f;
                break;
            case "http://www.edamam.com/ontologies/edamam.owl#Measure_gallon":
            case "http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram":
                quantity = .1f;
                break;
            case "http://www.edamam.com/ontologies/edamam.owl#Measure_milliliter":
                quantity = 1000f;
                break;
            case "http://www.edamam.com/ontologies/edamam.owl#Measure_drop":
                quantity = 10f;
                break;
        }
        return quantity;
    }

    public static <T> List<T> getDuplicates(Collection<T> collection) {
        Set<T> duplicates = new LinkedHashSet<>();
        Set<T> uniques = new HashSet<>();

        for (T t : collection) {
            if (!uniques.add(t)) {
                duplicates.add(t);
            }
        }

        return new ArrayList<>(duplicates);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
