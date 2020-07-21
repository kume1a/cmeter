package com.kumela.cmeter.common;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Toko on 03,July,2020
 **/

public class Utils {
    public static String format(float f) {
        String s = Float.toString(f);
        return s.contains(".") ? s.replaceAll("0*$", "").replaceAll("\\.$", "") : s;
    }

//    public static int pxToDp(Context context, float px) {
//        return (int) (px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
//    }
}
