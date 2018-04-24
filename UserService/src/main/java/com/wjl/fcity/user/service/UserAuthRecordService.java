package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.UserAuthRecord;
import com.wjl.fcity.user.request.AuthRecordReq;

/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-03-30 10:09
 */
public interface UserAuthRecordService {

    /**
     * 据用户的userId查询用户用户认证记录表
     *
     * @param userId       用户的userId
     * @param authCategory 认证类别[1: 居民登记中心, 2: 银行, 3: 手机营业厅, 4: 购物中心]
     * @param authItem     认证项目[1: 实名认证, 2: 身份证照片, 3: 人脸认证, 4: 信用卡认证, 5: 银行卡认证, 6: 信用卡邮箱, 7: 运营商认证, 8: 支付宝认证, 9: 淘宝认证]
     * @return UserAuthRecord
     */
    UserAuthRecord findUserAuthRecordByUserId(Long userId, Integer authCategory, Integer authItem);

    /**
     * 更新用户认证记录表状态
     *
     * @param userAuthRecord 用户认证记录表对象
     */
    void updateUserAuthRecordStatus(UserAuthRecord userAuthRecord);

    /**
     * 将用户认证信息保存到数据库中
     *
     * @param userAuthRecord 用户认证对象
     */
    void insertUserAuthRecord(UserAuthRecord userAuthRecord);

    /**
     * 保存及修改认证纪录
     *
     * @param authRecordReq 认证记录接口调用请求体
     * @return 是否保存成功
     */
    boolean saveOrUpdateAuthRecord(AuthRecordReq authRecordReq);
}
