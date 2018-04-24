package com.wjl.fcity.user.service;


import com.wjl.fcity.user.po.LoginRecordPO;

/**
 * @author czl
 */
public interface LoginRecordService {
    /**
     * 插入用户登陆信息
     *
     * @param loginRecordPO 用户登录信息
     */
    void addLoginRecord(LoginRecordPO loginRecordPO);
}
