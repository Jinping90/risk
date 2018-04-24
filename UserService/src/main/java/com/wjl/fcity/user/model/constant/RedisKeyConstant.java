package com.wjl.fcity.user.model.constant;

/**
 * redis key 常量
 *
 * @author shengju
 */
public class RedisKeyConstant {

    private RedisKeyConstant() {
    }

    /**
     * 登录token
     */
    public static final String TOKEN_KEY = "fCity:token:%d";
    public static final String OLD_TOKEN_KEY = "fCity:token:old:%d";
}
