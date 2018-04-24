package com.wjl.fcity.user.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;

/**
 * @author : Fy
 * @date : 2018-04-01 11:20
 * 汉字拼音排序比较器(依据字符串首位字符的ASCII码)
 */
public class SpellComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        try {
            String s1 = new String(o1.toString().getBytes("GB2312"), "ISO-8859-1");
            String s2 = new String(o2.toString().getBytes("GB2312"), "ISO-8859-1");
            return s1.compareTo(s2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
