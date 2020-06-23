package com.hopechart.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.internal.$Gson$Preconditions;


/**
 * Description:SharedPreferences工具类
 *
 * @author lincq
 * @date 2019/4/18
 */
public class SharedPreferencesUtils {
    public static final String SHARED_PRE_NAME = "eyes";

    private static SharedPreferences sp;

    public synchronized static void init(Context context) {
        if (context != null) {
            sp = context.getSharedPreferences(SHARED_PRE_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     * 存取int类型
     */
    public static void putInt(String key, int value) {
        if (sp != null) {
            sp.edit().putInt(key, value).apply();
        }
    }

    public static int getInt(String key) {
        if (sp != null) {
            return sp.getInt(key, 0);
        }
        return 0;
    }

    /**
     * 存取Boolean类型
     */
    public static void putBoolean(String key, boolean value) {
        if (sp != null) {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (sp != null) {
            return sp.getBoolean(key, defValue);
        }
        return defValue;
    }

    //存取String类型
    public static String getString(String key, String defValue) {
        if (sp != null)
            return sp.getString(key, defValue);
        return defValue;
    }

    public static void putString(String key,String value){
        if(sp != null){
            sp.edit().putString(key,value).apply();
        }
    }

}
