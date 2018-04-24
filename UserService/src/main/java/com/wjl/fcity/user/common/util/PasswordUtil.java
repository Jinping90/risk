package com.wjl.fcity.user.common.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * 密码加密工具类
 *
 * @author czl
 */
public class PasswordUtil {

    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * 盐的长度
     */
    private static final int SALT_BYTE_SIZE = 16 / 2;

    /**
     * 生成密文的长度
     */
    private static final int HASH_BIT_SIZE = 128 / 2;

    /**
     * 迭代次数
     */
    private static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 获取加密后的密码值
     *
     * @param psw 密码
     * @return 加密后的密码值
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    public static String getPassword(String psw)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateByteSalt();
        String epsw = getEncryptedPassword(psw, salt);
        return toHex(salt) + epsw;
    }

    /**
     * 验证密码是否正确
     *
     * @param hashPsw hashPsw
     * @param psw     密码
     * @return 密码是否正确
     * @throws InvalidKeySpecException  InvalidKeySpecException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public static boolean validatePassword(String hashPsw, String psw)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = hashPsw.substring(0, 16);
        return hashPsw.equals(salt + getEncryptedPassword(psw, salt));
    }


    /**
     * 通过提供加密的强随机数生成器 生成盐
     *
     * @return byte[]
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private static byte[] generateByteSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return salt;
    }

    private static String getEncryptedPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        return getEncryptedPassword(password, fromHex(salt));
    }

    private static String getEncryptedPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
                PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return toHex(f.generateSecret(spec).getEncoded());
    }

    /**
     * 十六进制字符串转二进制字符串
     *
     * @param hex the hex string
     * @return the hex string decoded into a byte array
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(
                    hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * 二进制字符串转十六进制字符串
     *
     * @param array the byte array to convert
     * @return a length*2 character string encoding the byte array
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        String psw = "DC483E80A7A0BD9EF71D8CF973673924";
        System.out.println(psw);
        System.out.println(getPassword(psw));
        System.out.println(validatePassword("6c08ff6dfad35dc8a76334cab5c83569", psw));
    }


}