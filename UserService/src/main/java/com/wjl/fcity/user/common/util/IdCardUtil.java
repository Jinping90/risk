package com.wjl.fcity.user.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fy
 */
public class IdCardUtil {

    /**
     * 身份证号码中的出生日期的格式.
     */
    private final static String BIRTH_DATE_FORMAT = "yyyyMMdd";

    /**
     * 身份证的最小出生日期,1900年1月1日。
     */
    private final static Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L);

    private final static int NEW_CARD_NUMBER_LENGTH = 18;

    /**
     * 18位身份证中最后一位校验码
     */
    private final static char[] VERIFY_CODE = {'1', '0', 'X', '9', '8', '7',
            '6', '5', '4', '3', '2'};

    /**
     * 18位身份证中，各个数字的生成校验码时的权值
     */
    private final static int[] VERIFY_CODE_WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1,
            6, 3, 7, 9, 10, 5, 8, 4, 2};

    public static boolean validate(String cardNumber) {
        boolean result;
        //身份证号不能为空.
        result = null != cardNumber;
        //身份证号长度是18(新证).
        result = result && NEW_CARD_NUMBER_LENGTH == cardNumber.length();
        // 身份证号的前17位必须是阿拉伯数字
        for (int i = 0; result && i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            result = ch >= '0' && ch <= '9';
        }
        // 身份证号的第18位校验正确
        result = result
                && (calculateVerifyCode(cardNumber) == cardNumber
                .charAt(NEW_CARD_NUMBER_LENGTH - 1));
        // 出生日期不能晚于当前时间，并且不能早于1900年
        try {
            Date birthDate = getBirthDate(cardNumber);
            result = result && birthDate.before(new Date());
            result = result && birthDate.after(MINIMAL_BIRTH_DATE);
            //出生日期中的年、月、日必须正确,比如月份范围是[1,12],日期范围是[1,31]，还需要校验闰年、大月、小月的情况时，月份和日期相符合
            String birthdayPart = getBirthDayPart(cardNumber);
            String realBirthdayPart = createBirthDateParser().format(birthDate);
            result = result && (birthdayPart.equals(realBirthdayPart));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private static Date getBirthDate(String cardNumber) {
        Date cacheBirthDate;
        try {
            cacheBirthDate = new SimpleDateFormat(BIRTH_DATE_FORMAT)
                    .parse(getBirthDayPart(cardNumber));
        } catch (Exception e) {
            throw new RuntimeException("身份证的出生日期无效");
        }
        return new Date(cacheBirthDate.getTime());
    }


    private static String getBirthDayPart(String cardNumber) {
        return cardNumber.substring(6, 14);
    }

    /**
     * 校验码（第十八位数）：
     * <p>
     * 十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和；
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
     * 2; 计算模 Y = mod(S, 11)< 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9
     * 8 7 6 5 4 3 2
     *
     * @param cardNumber 身份证号码
     * @return char
     */
    private static char calculateVerifyCode(CharSequence cardNumber) {
        int sum = 0;
        for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            sum += (ch - '0') * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

    private static SimpleDateFormat createBirthDateParser() {
        return new SimpleDateFormat(BIRTH_DATE_FORMAT);
    }


}
