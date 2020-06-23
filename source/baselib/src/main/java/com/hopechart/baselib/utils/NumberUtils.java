package com.hopechart.baselib.utils;

import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @time 2020/5/12
 * @user 张一凡
 * @description 数字转换工具类
 * @introduction
 */
public class NumberUtils {

    //将元转换为万元并保留小数点后两位
    public static String formatYuanOrWanYuan(String yuan) {
        return formatYuanOrWanYuan(yuan, false);
    }

    public static String formatYuanOrWanYuan(String yuan, boolean withUnit) {
        String result = "0.00";
        double yuanD = 0;
        if (TextUtils.equals(yuan, "-"))
            result = "-";
        else if (TextUtils.isEmpty(yuan))
            result = "0.00";
        else {
            try {
                yuanD = Double.parseDouble(yuan);
                if (Math.abs(yuanD) < 10000)
                    result = twoEffectiveNumber(yuanD);
                else {
                    result = twoEffectiveNumber(yuanD / 10000);
                }
            } catch (NumberFormatException e) {
                result = "0.00";
            }
        }
        String unit = Math.abs(yuanD) < 10000 ? "元" : "万元";
        result = result + (withUnit ? unit : "");
        return result;
    }

    //根据输入数据返回元或者万元
    public static String formatMoneyUnit(String yuan) {
        if (TextUtils.equals(yuan, "-"))
            return "元";
        if (TextUtils.isEmpty(yuan))
            return "元";
        String result = "元";
        try {
            double yuanD = Double.parseDouble(yuan);
            if (yuanD < 10000)
                return "元";
            return "万元";
        } catch (NumberFormatException e) {
            result = "元";
        }

        return result;
    }

    //设置价格
    public static String formatYuan(String str) {
        return twoEffectiveNumber(str) + "元";
    }


    //小数点后保留两位有效数字
    public static String twoEffectiveNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return "0.00";
        }
        double result;
        try {
            result = Double.parseDouble(number);
            return twoEffectiveNumber(result);
        } catch (NumberFormatException ex) {
            result = 0.00;
        }
        return String.valueOf(result);
    }

    //将一个double类型的数据保留小数点后两位
    public static String twoEffectiveNumber(double number) {
        BigDecimal bigDecimal = new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP);
        //设置想要的格式
        DecimalFormat format = new DecimalFormat("0.00#");
        return format.format(bigDecimal);
    }

    //保留小数点后两位小数，不够不补0
    public static String twoEffectiveNumberNoAddZero(String number) {
        if (TextUtils.isEmpty(number)) {
            return "0";
        }
        double result;
        try {
            result = Double.parseDouble(number);
            return twoEffectiveNumberNoAddZero(result);
        } catch (NumberFormatException ex) {
            result = 0;
        }
        return String.valueOf(result);
    }

    public static String twoEffectiveNumberNoAddZero(double number) {
        BigDecimal bigDecimal = new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP);
        //设置想要的格式
        DecimalFormat format = new DecimalFormat("0.##");
        return format.format(bigDecimal);
    }

    //将String类型转换为Double类型
    public static double strToDouble(String number) {
        if (TextUtils.isEmpty(number))
            return 0D;
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.doubleValue();
    }

    public static int strToInt(String number){
        return strToInt(number,0);
    }

    //将String转换为Int类型
    public static int strToInt(String number,int defaultNumber) {
        if (number == null || TextUtils.isEmpty(number))
            return defaultNumber;
        //如果包含小数点，则取小数点前面的数据
        if (number.contains(".")) {
            String[] result = number.split("\\.");
            if (result.length != 2)
                return defaultNumber;
            else
                number = result[0];
        }
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return defaultNumber;
        }
    }


    //根据传入的数据判断文件大小
    public static double getFileSize(double size){
        if(size < 1024)
            return size;
        else if(size < 1024 * 1024)
            return size / 1024;
        else
            return size / 1024 /1024;
    }

    //根据传入的数据判断文件单位
    public static String getFileSizeUnit(double size){
        if(size < 1024)
            return "KB";
        else if(size < 1024 * 1024)
            return "MB";
        else
            return "GB";
    }
}
