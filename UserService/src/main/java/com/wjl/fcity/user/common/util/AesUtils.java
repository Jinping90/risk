package com.wjl.fcity.user.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author czl
 */
public class AesUtils {


    /**
     * AES 加密
     *
     * @param content    加密内容
     * @param encryptKey 加密秘钥
     * @return 加密数组
     * @throws Exception exception
     */
    private static byte[] aesEncrypt(String content, String encryptKey) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(encryptKey.getBytes("UTF-8"));
        keyGen.init(128, secureRandom);
        SecretKey secretKey = keyGen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(content.getBytes("UTF-8"));
    }

    /**
     * AES 解密
     *
     * @param content    解密内容
     * @param encryptKey 解密秘钥
     * @return 解密后的content
     * @throws Exception Exception
     */
    private static String aesDecrypt(byte[] content, String encryptKey) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(encryptKey.getBytes("UTF-8"));
        keyGen.init(128, secureRandom);
        SecretKey secretKey = keyGen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(content);
        return new String(result, "UTF-8");
    }


    /**
     * 获取AES加密后的 十六进制字符串
     *
     * @param content    加密内容
     * @param encryptKey 秘钥
     * @return AES加密后的 十六进制字符串
     * @throws Exception Exception
     */
    public static String aesEncryptHexString(String content, String encryptKey) throws Exception {
        return parseByte2HexString(aesEncrypt(content, encryptKey));
    }


    /**
     * 解密AES加密后的 十六进制字符串
     *
     * @param hexContent 加密的content
     * @param encryptKey 秘钥
     * @return AES加密后的 十六进制字符串
     * @throws Exception Exception
     */
    public static String aesDecryptHexString(String hexContent, String encryptKey) throws Exception {
        return aesDecrypt(parseHexStr2Byte(hexContent), encryptKey);
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buffer 二进制
     * @return 16进制
     */
    private static String parseByte2HexString(byte[] buffer) {
        StringBuilder sb = new StringBuilder();
        for (byte aBuffer : buffer) {
            String hex = Integer.toHexString(aBuffer & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexString 16进制
     * @return 二进制
     */
    private static byte[] parseHexStr2Byte(String hexString) {
        int two = 2;
        if (hexString.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexString.length() / two];
        for (int i = 0; i < hexString.length() / two; i++) {
            int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
