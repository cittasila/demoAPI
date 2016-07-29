package com.langying.toolbox.utils;

import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.springframework.security.access.prepost.PostAuthorize;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 */
@CompileStatic
@TypeChecked
public class DateUtil {

    /**
     * 格式化 时间
     *
     * @param time
     * @param fmt  ,fmt为空的时候，默认是yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTime(Timestamp time, String fmt) {
        if (time != null) {
            if (fmt == null) fmt = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat myFormat = new SimpleDateFormat(fmt);
            return myFormat.format(time);
        } else {
            return "";
        }
    }

    /**
     * 格式化 时间
     *
     * @param strDateTime
     * @return
     */
    public static Long formatDateTime(String strDateTime) throws ParseException {
        if (strDateTime == null || "".equals(strDateTime)) {
            return null;
        } else {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date myDate = myFormat.parse(strDateTime.trim());
            return myDate.getTime();
        }
    }

    /**
     * 格式化 时间
     *
     * @param strDateTime
     * @return
     */
    public static Long formatDate(String strDateTime) throws ParseException {
        if (strDateTime == null || "".equals(strDateTime)) {
            return null;
        } else {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormat.parse(strDateTime.trim());
            return myDate.getTime();
        }
    }

    /**
     * 格式化毫秒数 时间
     *
     * @param time
     * @param fmt  ,fmt为空的时候，默认是yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTime(Long time, String fmt) {
        return formatTime(getTime(time), fmt);
    }

    /**
     * 获取当前 时间
     *
     * @return
     */
    public static Timestamp getTime() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取今天开始时间
     * yyyy-MM-dd 00:00:00
     *
     * @return
     */
    public static Timestamp getDateFirst() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取今天最后一秒时间
     * yyyy-MM-dd 23:59:59
     *
     * @return
     */
    public static Timestamp getDateLast() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar calendar = Calendar.getInstance();
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取今天 日期
     *
     * @return
     */
    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获取 今天的月份
     * 返回格式 yyyyMM
     *
     * @return
     */
    public static String getMonth() {
        return formatDate(getTime(), "yyyyMM");
    }

    /**
     * 根据毫秒数 获取当前时间
     *
     * @param time
     * @return
     */
    public static Timestamp getTime(Long time) {
        return new Timestamp(time);
    }

    /**
     * 根据毫秒数 格式化成 时间格式
     *
     * @param time
     * @param fmt
     * @return
     */
    public static String formatDate(Long time, String fmt) {
        return formatDate(getTime(time), fmt);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param fmt  ,fmt为空，默认是 yyyy-MM-dd
     * @return
     */
    public static String formatDate(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        if (fmt == null) fmt = "yyyy-MM-dd";
        SimpleDateFormat myFormat = new SimpleDateFormat(fmt);
        return myFormat.format(date);
    }


}
