package com.hopechart.baselib.utils;

import android.text.TextUtils;
import android.util.Log;

import com.hopechart.baselib.BaseApplication;

public class Logs {

    public static String currentTag = "";

    public static void e(String message) {
        String packageName = BaseApplication.getInstance().getPackageName();
        e(packageName, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

    public static void tagE(String message) {
        if (!TextUtils.isEmpty(currentTag))
            e(currentTag, message);
        else
            e(message);
    }
}
