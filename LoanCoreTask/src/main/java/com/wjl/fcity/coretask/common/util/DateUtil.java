package com.wjl.fcity.coretask.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author czl
 */
public class DateUtil {

    /**
     * 获取n天前的时间
     *
     * @param day 天数
     * @return 时间
     */
    public static Date getFewDaysAgoDate(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.DATE, -day);
        return calendar.getTime();
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date 时间
     * @return String
     */
    public static String dateStr4(Date date) {
        return dateStr(date);

    }

    /**
     * 日期转换为字符串 格式自定义
     *
     * @param date 时间
     * @return String
     */
    private static String dateStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取当前系统时间前?小时的时间戳
     */
    public static Date getHourAgo(Long nowTime, int hour) {
        Long time = nowTime - hour * 1000 * 60 * 60;
        return new Date(time);
    }

    /**
     * 获取指定时间的年
     *
     * @param date 指定时间
     * @return 年
     */
    public static int getTimeYear(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取指定时间的月
     *
     * @param date 指定时间
     * @return 月
     */
    public static int getTimeMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 前/后?天
     *
     * @param date 指定时间
     * @param hour date 指定时间
     * @return Date
     */
    public static Date rollHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }

    /**
     * 计算当天的开始时间和结束时间
     */
    public static Date getDiffer(int differentDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, differentDay);
        return calendar.getTime();
    }
}
