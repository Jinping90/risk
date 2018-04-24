package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.vo.IdentifyFaceAuthVO;
import com.wjl.fcity.user.po.UserPO;


/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-03-29 10:10
 */
public interface IdentifyFaceAuthService {


    /**
     * 人脸识别
     * @param user user
     * @param identifyFaceAuthForm 人脸认证
     */
    void toUserFaceVerifyInfo(UserPO user, IdentifyFaceAuthVO identifyFaceAuthForm);
}
