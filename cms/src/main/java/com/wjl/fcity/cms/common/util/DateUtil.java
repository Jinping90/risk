package com.wjl.fcity.cms.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author czl
 */
public class DateUtil {

    /**
     * 将时间戳转为LocalDate
     *
     * @param dateTime 时间戳
     * @return LocalDate
     */
    public static LocalDate dateLongToLocalDate(Long dateTime) {
        Date date = new Date(dateTime);
        Instant startInstant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的startLocalDate。
        return startInstant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time 时间字符串
     * @return 时间戳
     */
    public static Long stringToDateLong(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    /**
     * 将yyyyMMdd格式的时间字符串转为时间戳
     *
     * @param userTime 时间字符串
     * @return 时时间戳
     */
    public static long endDateToLong(String userTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long l = 0L;
        try {
            l = sdf.parse(userTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 获取当天最后一毫秒的时间戳
     */
    public static long getNowEnd() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis();
    }

    /**
     * 获取当天开始的时间戳
     */
    public static long getNowStart() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }


    /**
     * 获取现在的时间戳
     */
    public static long getNow() {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }


}
