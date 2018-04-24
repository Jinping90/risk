package com.wjl.fcity.welfare.service;


import com.wjl.fcity.welfare.model.User;

/**
 * @author czl
 */
public interface LoginRecordService {
    /**
     * 插入用户登陆信息
     * @param user 用户信息
     * @param deviceId 用户设备号
     */
    void insert(User user, String deviceId);
}
