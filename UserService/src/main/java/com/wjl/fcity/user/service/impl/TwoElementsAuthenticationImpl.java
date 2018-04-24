package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.mapper.TwoElementsAuthenticationMapper;
import com.wjl.fcity.user.model.TwoElementAuth;
import com.wjl.fcity.user.service.TwoElementsAuthenticationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : Fy
 * @date : 2018-03-30 16:27
 */
@Service
public class TwoElementsAuthenticationImpl implements TwoElementsAuthenticationService {

    @Resource
    private TwoElementsAuthenticationMapper twoElementsAuthenticationMapper;

    /**
     * 根据用户的userId来查找该用户的二要素认证记录表记录
     *
     * @param userId 用户的userId
     * @return TwoElementAuth
     */
    @Override
    public TwoElementAuth findTwoElementAuthByUserId(Long userId) {
        return twoElementsAuthenticationMapper.findTwoElementAuthByUserId(userId);
    }
}
