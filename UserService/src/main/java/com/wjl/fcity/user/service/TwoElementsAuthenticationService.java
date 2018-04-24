package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.TwoElementAuth;

/**
 * @author : Fy
 * @date : 2018-03-30 16:26
 */
public interface TwoElementsAuthenticationService {

    /**
     * 根据用户的userId来查找该用户的二要素认证记录表记录
     *
     * @param userId 用户的userId
     * @return TwoElementAuth
     */
    TwoElementAuth findTwoElementAuthByUserId(Long userId);


}
