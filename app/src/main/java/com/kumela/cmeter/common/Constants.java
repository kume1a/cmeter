package com.kumela.cmeter.common;

import com.kumela.cmeter.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Toko on 20,June,2020
 **/

public class Constants {

    private Constants() {
    }

    // NutritionX api data
    public static final String BASE_URL = "https://trackapi.nutritionix.com/";

    public static final String URL_PATH_NATURAL_NUTRIENTS = "v2/natural/nutrients";
    public static final String URL_PATH_SEARCH_INSTANT = "v2/search/instant";

    public static final String APP_ID = "3f5d2bd8";
    public static final String API_KEY = "19bdade91c74e3807a70455b59515da2";

    // Authentication field validation
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Zaa-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String VALID_ASCII_REGEX = "\\A\\p{ASCII}*\\z";

    // OnBoarding starter data
    public static final int MIN_WEIGHT = 40;
    public static final int AVERAGE_WEIGHT = 60;
    public static final int MAX_WEIGHT = 150;

    public static final int MIN_AGE = 8;
    public static final int AVERAGE_AGE = 18;
    public static final int MAX_AGE = 120;

    public static final int MIN_HEIGHT = 100;
    public static final int AVERAGE_HEIGHT = 160;
    public static final int MAX_HEIGHT = 250;

    // Firebase
    public static final String CHILD_USERS = "users";
    public static final String CHILD_PRODUCTS = "products";

    public static final String UID_DATE = "uid_date";

    public static final String BREAKFAST = "BREAKFAST";
    public static final String DINNER = "DINNER";
    public static final String SUPPER = "SUPPER";
    public static final String SNACKS = "SNACKS";

    public static final Map<String, Integer> MEAL_TYPE_TO_STRING = new HashMap<String, Integer>() {{
        put(BREAKFAST, R.string.breakfast);
        put(DINNER, R.string.dinner);
        put(SUPPER, R.string.supper);
        put(SNACKS, R.string.snacks);
    }};

    public static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
}
