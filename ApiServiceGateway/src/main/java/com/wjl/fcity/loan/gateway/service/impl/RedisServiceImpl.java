package com.wjl.fcity.loan.gateway.service.impl;

import com.wjl.fcity.loan.gateway.service.RedisService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * redis
 *
 * @author shengju
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private JedisPool jedisPool;

    @Override
    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }



    @Override
    public void psetex(String key, Long timeout, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.psetex(key, timeout, value);
        jedis.close();
    }

}
