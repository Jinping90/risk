package com.wjl.fcity.mall.common.util;

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
    public static final Long ONE_DAY_MS = 24 * 60 * 60 * 1000L;

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
}
