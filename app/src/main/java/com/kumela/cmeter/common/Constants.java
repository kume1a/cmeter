package com.kumela.cmeter.common;

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


}
