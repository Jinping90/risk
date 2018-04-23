package com.wjl.fcity.loan.gateway.service;

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
     * 根据key获取value
     * @param key key
     * @return value
     */
    String get(String key);

    /**
     * 设置带过期时间的数据
     * @param key key
     * @param timeout 过期时间（秒）
     * @param value value
     */
    void psetex(String key, Long timeout, String value);

}
