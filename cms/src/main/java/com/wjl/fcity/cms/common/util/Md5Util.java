package com.wjl.fcity.cms.common.util;

import java.security.MessageDigest;

/**
 * @author czl
 */
public class Md5Util {

    private static final String SALT = "iqurongQJQ";

    public static String encode(String password) {
        password = password + SALT;
        return processEncode(password);
    }

    private static String processEncode(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            char[] charArray = password.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }

            byte[] md5Bytes = md5.digest(byteArray);
            StringBuilder hexValue = new StringBuilder();

            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;

                if (val < 16) {
                    hexValue.append("0");
                }

                hexValue.append(Integer.toHexString(val));
            }

            return hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}