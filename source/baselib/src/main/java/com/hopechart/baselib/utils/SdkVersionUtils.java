package com.hopechart.baselib.utils;

import android.os.Build;

/**
 * 判断SDK的版本信息
 */
public class SdkVersionUtils {

    //判断sdk是否大于等于8.0
    public static boolean checkAndroidO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

}
