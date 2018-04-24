package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.model.TwoElementAuth;

/**
 * @author xuhaihong
 * @date 2018-03-30 11:57
 **/
public interface TwoElementsAuthService {

    /**
     * 保存二要素
     *
     * @param twoElementsAuth 二要素
     */
    void save(TwoElementAuth twoElementsAuth);

    /**
     * 查询二要素
     *
     * @param twoElementsAuth 二要素
     * @return 二要素结果
     */
    TwoElementAuth getTwoElementAuth(TwoElementAuth twoElementsAuth);

    /**
     * 校验是否做过二要素验证
     * 查询结果为空 表示没做过验证
     *
     * @param twoElementsAuth 用户
     * @return 查询结果 false 没有验证 true 表示验证完成
     */
    Boolean checkTwoElementAuthRecord(TwoElementAuth twoElementsAuth);

    /**
     * 校验是否做过二要素验证
     * 查询结果为空 表示没做过验证
     *
     * @param userId 用户id
     * @return 查询结果 false 没有验证 true 表示验证完成
     */
    TwoElementAuth checkTwoElementAuthRecordByUserId(Long userId);

    /**
     * 二要素认证
     *
     * @param userId       用户id
     * @param realName     姓名
     * @param idCardNo     身份证号
     * @param inviteUserId 邀请人的userId
     * @return 结果
     */
    Response identify(Long userId, String realName, String idCardNo, Long inviteUserId);

}
