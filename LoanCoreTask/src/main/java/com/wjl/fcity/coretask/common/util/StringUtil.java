package com.wjl.fcity.coretask.common.util;

import java.util.List;

/**
 * 字符串处理工具类
 *
 * @author czl
 */
public class StringUtil {

    /**
     * 将集合元素用 "," 分隔
     *
     * @param list long集合
     * @return String
     */
    public static String listToString(List<Long> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Long aLong : list) {
            stringBuilder.append(aLong);
            stringBuilder.append(",");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
