package com.wjl.fcity.user.service;

import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.model.PromotionUser;
import com.wjl.fcity.user.model.vo.CityInfoVO;
import com.wjl.fcity.user.model.vo.UserAuthReportVO;
import com.wjl.fcity.user.model.vo.UserInfoVO;
import com.wjl.fcity.user.po.CityConfigPO;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.request.UserReq;

import java.util.List;
import java.util.Map;

/**
 * @author czl
 */
public interface UserService {
    /**
     * 根据用户手机号，获取用户信息
     *
     * @param mobile 手机号
     * @return User
     */
    UserPO findByMobile(String mobile);

    /**
     * 根据用户ID 查询用户
     *
     * @param userId 用户ID
     * @return user
     */
    UserPO findOne(Long userId);

    /**
     * 校验验证码是否正确
     *
     * @param mobile    手机号
     * @param vCode     验证码
     * @param noteToken 验证码token
     * @param type      发送验证码的类型
     * @return codeEnum
     */
    CodeEnum verifyVCode(String mobile, String vCode, String noteToken, Integer type);

    /**
     * 验证码登录
     *
     * @param userReq userVO
     * @param ip      ip
     * @return response
     */
    Map vCodeLogin(UserReq userReq, String ip);


    /**
     * 用户登录
     *
     * @param userReq userVo
     * @param ip      IP地址
     * @return response
     */
    Map login(UserReq userReq, String ip);

    /**
     * 用户注册
     *
     * @param userReq userVo
     * @param ip      ip地址
     * @return response
     */
    Map register(UserReq userReq, String ip);

    /**
     * 设置密码
     *
     * @param userReq userVo
     * @return response
     */
    CodeEnum serPassword(UserReq userReq);

    /**
     * 忘记密码，修改密码
     *
     * @param userReq userVo
     * @return response
     */
    CodeEnum resetPassword(UserReq userReq);

    /**
     * 用户推出登录
     *
     * @param userId userId
     * @return response
     */
    CodeEnum loginOut(Long userId);

    /**
     * 获取用户基本信息
     *
     * @param userId userId
     * @return response
     */
    UserInfoVO getUserInfo(Long userId);

    /**
     * 获取建筑状态
     *
     * @return Response
     */
    List<CityInfoVO> getBuildingStatus();

    /**
     * 获取建筑状态
     *
     * @param userId userId
     * @return Response
     */
    List<CityInfoVO> getUserBuildingStatus(Long userId);

    /**
     * 获取用户认证信息
     *
     * @param userId userId
     * @return Response
     */
    List<UserAuthReportVO> getUserAuthInfo(Long userId);

    /**
     * 根据用户的 userId，更新用户的信用值
     *
     * @param userId      用户的userId
     * @param creditValue 信用值
     */
    void updateUserCreditValueById(Long userId, Integer creditValue);


    /**
     * 修改用户的信用值
     *
     * @param userId                用户ID
     * @param authItem              认证项目
     * @param changeCreditValueEnum 信用值修改类型
     */
    void updateCreditValue(Long userId, Integer authItem, Integer changeCreditValueEnum);

    /**
     * 校验用户手机号是否注册
     *
     * @param phone 电话号码
     * @return true 已存在  false 不存在
     */
    Boolean checkPhoneExist(String phone);


    /**
     * 二要素认证通过后  更新用户姓名
     *
     * @param userId 用户id
     * @param name   名称
     * @param gender 性别
     */
    void updateUserName(Long userId, String name, Integer gender);

    /**
     * 更新推荐人
     *
     * @param userId        推荐人id
     * @param mobile        手机号
     * @param promotionUser 被推荐人注册信息
     */
    void updateInviteUserId(Long userId, String mobile, PromotionUser promotionUser);

    /**
     * 查询已邀请用户个数
     *
     * @param userId 邀请用户id
     * @return 总数
     */
    Integer countInvites(Long userId);

    /**
     * 修改用户的信用值
     *
     * @param userId       用户ID
     * @param creditValues 信用值
     */
    void updateCreditValueByInvite(Long userId, Integer creditValues);

    /**
     * 保存用户的初次登陆的DeviceId
     *
     * @param deviceId 唯一设备id
     */
    void insertDeviceId(String deviceId);
}
