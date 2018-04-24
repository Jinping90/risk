package com.wjl.fcity.coretask.service;


import java.util.List;

/**
 * 用户Service
 *
 * @author czl
 */
public interface UserService {

    /**
     * 更新用户信用值
     *
     * @param creditValue 信用值
     * @param userIdList  需要修改的用户id集合
     */
    void updateUserAuthRecordStatus(Integer creditValue, List<Long> userIdList);
}
