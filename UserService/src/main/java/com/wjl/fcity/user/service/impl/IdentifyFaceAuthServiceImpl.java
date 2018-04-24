package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.model.vo.IdentifyFaceAuthVO;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.service.IdentifyFaceAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-03-29 10:12
 */
@Service
@Slf4j
public class IdentifyFaceAuthServiceImpl implements IdentifyFaceAuthService {
    /**
     * 人脸识别
     */
    @Override
    public void toUserFaceVerifyInfo(UserPO user, IdentifyFaceAuthVO identifyFaceAuthForm) {


    }
}
