package com.wjl.fcity.user.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author czl
 */
public class DateUtil {

    /**
     * 一天的毫秒数
     */
    private static final Long ONE_DAY_MS = 24 * 60 * 60 * 1000L;

    /**
     * 传入日期加上传入天数 是否大于 当前时间
     *
     * @param date 日期
     * @param days 天数
     * @return boolean
     */
    public static boolean isMoreThanNow(Date date, int days) {
        return date.getTime() + days * ONE_DAY_MS > System.currentTimeMillis();
    }

    /**
     * 前/后?天
     *
     * @param date 指定时间
     * @param days 指定天数
     * @return Date
     */
    public static Date addDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
