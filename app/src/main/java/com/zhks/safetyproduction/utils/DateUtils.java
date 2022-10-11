package com.zhks.safetyproduction.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");//初始化Formatter的转换格式。

    public static SimpleDateFormat formatterYmd = new SimpleDateFormat("yyyy-MM-dd");//初始化Formatter的转换格式。

    private static SimpleDateFormat formatteryyMdd = new SimpleDateFormat("yyyy-M-dd");

    private static SimpleDateFormat formatterMd = new SimpleDateFormat("MM-dd");//初始化Formatter的转换格式。

    private static SimpleDateFormat formatterMs = new SimpleDateFormat("mm:ss");//初始化Formatter的转换格式。

    private static SimpleDateFormat formatterHMS = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。

    private static SimpleDateFormat formatterYMD = new SimpleDateFormat("yyyy年MM月dd日");//初始化Formatter的转换格式。

    private static SimpleDateFormat formatterYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//初始化Formatter的转换格式。

    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate() {
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return formatterYmd.format(date);
    }

    /**
     * 获取当前日期 时分秒
     * @return
     */
    public static String getCurrentDateYMDHMS(){
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return formatterYMDHMS.format(date);
    }


    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDateyyMdd() {
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return formatteryyMdd.format(date);
    }

    /**
     * 获取当前日期时分
     * @return
     */
    public static String getCurrentDateyyMMddHHmm() {
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static Date parseServerTime(String serverTime, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            if (TextUtils.isEmpty(serverTime)) {
                serverTime = getCurrentDate();
            }
            date = sdf.parse(serverTime);
        } catch (Exception e) {
            System.out.println(e);
        }
        return date;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String dateStr, SimpleDateFormat format) {
        try {
            return String.valueOf(format.parse(dateStr).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }


    public static String timeStamp2Date(String time) {
        Long timeLong = Long.parseLong(time);
        Date date;
        try {
            date = formatterYmd.parse(formatterYmd.format(timeLong));
            return formatterYmd.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String timeStamp2Date(String time, SimpleDateFormat formatter) {
        Long timeLong = Long.parseLong(time);
        Date date;
        try {
            date = formatter.parse(formatter.format(timeLong));
            return formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getTimeStr(long time){
        formatterMs.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatterMs.format(time);
    }
}
