package com.hopechart.baselib.utils;

import android.util.Log;
import com.hopechart.baselib.BaseApplication;

public class Logs {
    public static void e(String message) {
        String packageName = BaseApplication.getInstance().getPackageName();
        e(packageName, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }
}
