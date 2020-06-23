package com.hopechart.baselib.utils;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @time 2020/5/14
 * @user 张一凡
 * @description 日期转换类
 * @introduction
 */
public class DateUtils {

    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DF_YEAR_MONTH_DAY = "yyyy年MM月dd日";
    public static final String DF_HOUR_MINUTE = "HH:mm";
    public static final Long TIME_ONE_HOUR = 60 * 60 * 1000L;
    public static final Long TIME_ONE_MINUTE = 60 * 1000L;
    public static final Long TIME_ONE_SECOND = 1000L;

    //将时间转换为几分钟前等
    public static String formatSimpleTime(long time) {
        long now = System.currentTimeMillis();

        long diff = now - time;
        if (diff < 60 * 1000L) {
            return "几秒前";
        } else if (diff < 2 * 60 * 1000L) {
            return "一分钟前";
        } else if (diff < 60 * 60 * 1000L) {
            return "几分钟前";
        } else if (diff < 5 * 60 * 60 * 1000L) {
            return "一小时前";
        } else if (diff < 2 * 24 * 60 * 60 * 1000L) {
            return "一天前";
        } else {
            DateFormat format = new SimpleDateFormat("YYYY-MM-dd");
            return format.format(new Date(time));
        }
    }

    //格式化时间
    public static String formatTime(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    //将时间格式化为某天的上午或者下午
    public static String formatTimeAmPm(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        try {
            return formatTimeAmPm(simpleDateFormat.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatTimeAmPm(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DF_HOUR_MINUTE);
        String hour = simpleDateFormat.format(date);
        String[] hourStr = hour.split(":");
        int hourInt = NumberUtils.strToInt(hourStr[0]);
        if (hourInt > 12)
            return (hourInt - 12) + ":" + hourStr[1] + "PM";
        else
            return hour + "AM";
    }

    //将String转换为Long
    public static Long getTime(String time) throws ParseException {
        return getTime(time,DF_YYYY_MM_DD_HH_MM_SS);
    }

    @NotNull
    public static Long getTime(String time, String format) throws ParseException {
        if(TextUtils.isEmpty(time) || TextUtils.isEmpty(format))
            return -1L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = simpleDateFormat.parse(time);
        if (date != null)
            return date.getTime();
        else
            return -1L;
    }

    //判断时间是不是当天
    public static boolean isToday(String dateStr) {
        if (TextUtils.isEmpty(dateStr))
            return false;
        String today = formatTime(System.currentTimeMillis(), DF_YYYY_MM_DD);
        //截取时间前半部分
        String dateDay = dateStr.split(" ")[0];
        return TextUtils.equals(dateDay, today);
    }


    //查看当前需要显示多少时间格式
    public static int getTimeLength(Long time) {
        //如果大于1小时，则显示3位时间格式 即00:00:00
        if (time > TIME_ONE_HOUR)
            return 3;
        else if (time > TIME_ONE_MINUTE)
            return 2;
        else
            return 1;
    }

    //获取当前时间的小时数
    public static String getTimeHour(Long time) {
        int result = (int) (time / TIME_ONE_HOUR);
        String format = result > 9 ? String.valueOf(result) : "0" + result;
        return format;
    }

    //获取当前时间的分钟数
    public static String getTimeMinute(Long time) {
        time -= Integer.parseInt(getTimeHour(time)) * TIME_ONE_HOUR;
        int result = (int) (time / TIME_ONE_MINUTE);
        return result > 9 ? String.valueOf(result) : "0" + result;
    }

    //获取当前时间的秒数
    public static String getTimeSecond(Long time) {
        time -= Integer.parseInt(getTimeHour(time)) * TIME_ONE_HOUR;
        time -= Integer.parseInt(getTimeMinute(time)) * TIME_ONE_MINUTE;
        int result = (int) (time / TIME_ONE_SECOND);
        return result > 9 ? String.valueOf(result) : "0" + result;
    }
}
