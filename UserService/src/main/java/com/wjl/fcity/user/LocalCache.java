package com.wjl.fcity.user;

import com.wjl.fcity.user.model.LoginLimitData;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具
 *
 * @author Administrator
 */
@Slf4j
public class LocalCache {


    /**
     * 登陆的缓存，存储用户登陆失败次数
     */
    private static LoadingCache<String, LoginLimitData> LoginLimitCache = CacheBuilder.newBuilder()
            //当缓存项在指定的时间段内没有被读或写就会被回收
            .expireAfterAccess(30, TimeUnit.MINUTES)
            // 设置缓存个数
            .maximumSize(1000)
            .build(new CacheLoader<String, LoginLimitData>() {
                       @Override
                       // 当本地缓存命没有中时，调用load方法获取结果并将结果缓存
                       public LoginLimitData load(String key) {
                           LoginLimitData loginLimitData = new LoginLimitData();
                           loginLimitData.setCount(1);
                           loginLimitData.setLoginTime(System.currentTimeMillis());
                           return loginLimitData;
                       }
                   }
            );


    /**
     * 用户连续登陆失败3次,则提示"请尝试使用验证码登录或点击"忘记密码""
     */
    public static boolean hasMoreLoginFailRequest(String req) {
        LoginLimitData loginLimitData = LoginLimitCache.getIfPresent(req);
        Long now = System.currentTimeMillis();
        int maxErrTimes = 3;
        int thirthMinute = 30 * 60 * 1000;
        if (loginLimitData != null) {
            if (loginLimitData.getCount() > maxErrTimes && (now - loginLimitData.getLoginTime()) <= thirthMinute) {
                // 手机登陆失败3次,则30分钟之内不能再用密码登录。
                return true;
            } else {
                loginLimitData.setCount(loginLimitData.getCount() + 1);
                loginLimitData.setLoginTime(System.currentTimeMillis());
                LoginLimitCache.put(req, loginLimitData);
            }
        } else {
            loginLimitData = new LoginLimitData();
            loginLimitData.setCount(0);
            loginLimitData.setLoginTime(System.currentTimeMillis());
            LoginLimitCache.put(req, loginLimitData);
        }
        return false;
    }

    /**
     * 登陆成功,清除登陆限制缓存
     */
    public static void removeLoginLimit(String req) {
        LoginLimitCache.invalidate(req);
    }
}
