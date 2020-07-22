package com.kumela.cmeter.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Toko on 03,July,2020
 **/

public class Utils {
    public static String format(float f) {
        String s = Float.toString(f);
        return s.contains(".") ? s.replaceAll("0*$", "").replaceAll("\\.$", "") : s;
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_PATTERN, Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

//    public static int pxToDp(Context context, float px) {
//        return (int) (px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
//    }
}
