package com.wjl.fcity.cms.service;

import com.wjl.fcity.cms.entity.request.UserReq;

import java.util.Map;


/**
 * @author shengju
 */
public interface UserService {

    /**
     * 获取用户列表
     * @param userReq requestArgs
     * @return List<User>
     */
    Map<String, Object> getUserList(UserReq userReq);
}
