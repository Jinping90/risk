package com.wjl.fcity.user.service;


/**
 * redis操作
 *
 * @author shengju
 */
public interface RedisService {
    /**
     * 设置
     * @param key key
     * @param value value
     */
    void set(String key, String value);

    /**
     * 根据key删除
     * @param key key
     */
    void del(String key);


}
