package com.wjl.fcity.user.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 杨洋
 * @date 2018/1/11
 */
public class EmojiFilter {
    /**
     * 检测是否有emoji字符
     *
     * @param source 检查源
     * @return 一旦含有就抛出
     */
    private static boolean containsWord(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }

        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isNotEmojiCharacter(codePoint)) {
                return true;
            }
        }

        return false;
    }


    private static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source 检查源
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsWord(source)) {
            System.out.println("filterEmoji 不包含文字说明只有表情。");
            //如果不包含，直接返回
            return "";
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isNotEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }

        }

        if (buf == null) {
            //如果没有可能到这步吧！
            return "";
        } else {
            //这里的意义在于尽可能少的toString，因为会重新生成字符串
            if (buf.length() == len) {
                return source;
            } else {
                return buf.toString();
            }
        }

    }
}
