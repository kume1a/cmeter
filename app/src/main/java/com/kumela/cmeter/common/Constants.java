package com.kumela.cmeter.common;

/**
 * Created by Toko on 20,June,2020
 **/

public class Constants {

    private Constants() {
    }

    // edamam api data kumela011 package
    public static final String API_BASE_URL = "https://api.edamam.com/";
    public static final String API_PATH_FOOD_DATABASE = "api/food-database/v2/parser";
    public static final String API_PATH_NUTRIENTS = "api/food-database/v2/nutrients";
    public static final String API_PATH_SUGGESTIONS = "auto-complete";

    public static final String APP_ID = "c71cc990";
    public static final String APP_KEY = "8f5c59022517c0da1ed52e993ceeef10";

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

    // Firebase collections
    public static final String COLLECTION_USERS = "users";
    public static final String COLLECTION_USER_ADDED_FOODS = "user_added_foods";
    public static final String COLLECTION_PRODUCTS = "products";
    public static final String COLLECTION_PRODUCT_MEASURES = "product_measures";
    public static final String COLLECTION_PRODUCT_HISTORY = "product_history";
    public static final String COLLECTION_SEARCH_HISTORY = "search_history";


    // meal types TODO: 8/4/2020 refactor from strings to enums
    public static final String BREAKFAST = "BREAKFAST";
    public static final String DINNER = "DINNER";
    public static final String SUPPER = "SUPPER";
    public static final String SNACKS = "SNACKS";

    // default time format pattern
    public static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
}
