package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.vo.IdentifyFaceAuthVO;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.model.vo.IdentifyAuthVO;
import com.wjl.fcity.user.model.Response;

import java.text.ParseException;

/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-03-29 11:47
 */
public interface IdentifyAuthService {

    /**
     * 处理实名认证(身份证认证)
     *
     * @param identifyAuthFrom 页面上实名认证封装对象
     * @param user             用户的信息
     * @param birthdayAge      用户的生日
     * @param genderInt        性别[0: 男, 1: 女]
     * @throws ParseException 异常
     */
    void handleIdentifyAuth(IdentifyAuthVO identifyAuthFrom, UserPO user, String birthdayAge, Integer genderInt) throws ParseException;

    /**
     * 人脸识别接口
     *
     * @param identifyFaceAuthForm app端传过来的封装的参数
     * @param userId               用户的userId
     * @param ip                   app端的ip地址
     * @param name                 用户的名字
     * @param idCard               用户的身份证号码
     * @return Response
     */
    Response handFaceRecognitionAuth(IdentifyFaceAuthVO identifyFaceAuthForm, Long userId, String ip, String name, String idCard);

}
