package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.common.enums.AuthItemEnum;
import com.wjl.fcity.user.common.enums.AuthStatusEnum;
import com.wjl.fcity.user.common.util.DateUtil;
import com.wjl.fcity.user.mapper.ConfigAuthParamMapper;
import com.wjl.fcity.user.mapper.UserAuthRecordMapper;
import com.wjl.fcity.user.model.ConfigAuthParamDO;
import com.wjl.fcity.user.model.UserAuthRecord;
import com.wjl.fcity.user.request.AuthRecordReq;
import com.wjl.fcity.user.service.UserAuthRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author : Fy
 * @date : 2018-03-30 10:10
 */
@Slf4j
@Service
public class UserAuthRecordServiceImpl implements UserAuthRecordService {

    @Resource
    private UserAuthRecordMapper userAuthRecordMapper;
    @Resource
    private ConfigAuthParamMapper configAuthParamMapper;

    /**
     * 据用户的userId查询用户用户认证记录表
     *
     * @param userId       用户的userId
     * @param authCategory 认证类别[1: 居民登记中心, 2: 银行, 3: 手机营业厅, 4: 购物中心]
     * @param authItem     认证项目[1: 实名认证, 2: 身份证照片, 3: 人脸认证, 4: 信用卡认证, 5: 银行卡认证, 6: 信用卡邮箱, 7: 运营商认证, 8: 支付宝认证, 9: 淘宝认证]
     * @return UserAuthRecord
     */
    @Override
    public UserAuthRecord findUserAuthRecordByUserId(Long userId, Integer authCategory, Integer authItem) {
        return userAuthRecordMapper.findUserAuthRecordByUserId(userId, authCategory, authItem);
    }

    /**
     * 更新用户认证记录表状态
     *
     * @param userAuthRecord 用户认证记录表对象
     */
    @Override
    public void updateUserAuthRecordStatus(UserAuthRecord userAuthRecord) {
        userAuthRecordMapper.updateUserAuthRecordStatus(userAuthRecord);
    }

    /**
     * 将用户认证信息保存到用户认证记录表中
     *
     * @param userAuthRecord 用户认证对象
     */
    @Override
    public void insertUserAuthRecord(UserAuthRecord userAuthRecord) {

        userAuthRecordMapper.insertUserAuthRecord(userAuthRecord);

    }

    /**
     * 保存及修改认证纪录
     *
     * @param authRecordReq 认证记录接口调用请求体
     * @return 是否保存成功
     */
    @Override
    public boolean saveOrUpdateAuthRecord(AuthRecordReq authRecordReq) {
        Date now = new Date();
        Long userId = authRecordReq.getUserId();
        Integer authCategory = authRecordReq.getAuthCategory();
        Integer authItem = authRecordReq.getAuthItem();
        Integer authStatus = authRecordReq.getAuthStatus();

        //查询用户对应项目的认证记录
        UserAuthRecord userAuthReport = userAuthRecordMapper.findByUserIdAndItem(userId, authItem);
        if (userAuthReport == null) {
            //记录不存在时，则为第一次认证，查询新的认证记录
            insertNewReport(userId, authCategory, authItem, authStatus, now);
        } else {
            ConfigAuthParamDO configAuthParam = configAuthParamMapper.findByAuthItem(authItem);
            Integer status = userAuthReport.getStatus();
            //该认证项是否存在认证有效期
            if (configAuthParam != null && configAuthParam.getValidityPeriod() != null
                    && configAuthParam.getValidityPeriod() > 0
                    && status != null && AuthStatusEnum.PASS.getCode().equals(status)) {

                if (userAuthReport.getGmtModified() != null
                        && DateUtil.isMoreThanNow(userAuthReport.getGmtModified(), configAuthParam.getValidityPeriod())) {
                    //修改时间存在时，判断是否在有效期内该项认证项目已认证，则不修改
                    log.info("用户userId=" + userId + ", " + Objects.requireNonNull(AuthItemEnum.getEnumByCode(authItem)).getName() + "已认证通过并且还未过期, 不要重复认证");
                    return false;
                } else if (userAuthReport.getGmtModified() == null
                        && DateUtil.isMoreThanNow(userAuthReport.getGmtCreated(), configAuthParam.getValidityPeriod())) {
                    //修改时间不存在时，判断是否在有效期内该项认证项目已认证，则不修改
                    log.info("用户userId=" + userId + ", " + Objects.requireNonNull(AuthItemEnum.getEnumByCode(authItem)).getName() + "已认证通过并且还未过期, 不要重复认证");
                    return false;
                }
            }

            if (AuthStatusEnum.VERIFICATION.getCode().equals(authStatus)) {
                if (status != null && AuthStatusEnum.VERIFICATION.getCode().equals(status)) {
                    //如果已经是 `认证中` 的状态了，则不修改
                    log.info("用户userId=" + userId + ", " + Objects.requireNonNull(AuthItemEnum.getEnumByCode(authItem)).getName() + "已经是认证中状态, 不要重复认证");
                    return false;
                }

                userAuthReport.setAuthDetail("认证中");
            } else if (AuthStatusEnum.PASS.getCode().equals(authStatus)) {
                //增加认证次数
                if (userAuthReport.getAuthNum() != null && userAuthReport.getAuthNum() > 0) {
                    userAuthReport.setAuthNum(userAuthReport.getAuthNum() + 1);
                } else {
                    userAuthReport.setAuthNum(1);
                }
                userAuthReport.setAuthDetail("认证完成");
            } else if (AuthStatusEnum.NO_PASS.getCode().equals(authStatus)) {
                userAuthReport.setAuthDetail("认证失败");
            }

            userAuthReport.setStatus(authStatus);
            userAuthReport.setGmtModified(now);
            userAuthRecordMapper.update(userAuthReport);
        }
        return true;
    }

    /**
     * 插入新的认证纪录
     *
     * @param userId       用户ID
     * @param authCategory 认证类别
     * @param authItem     认证项目
     * @param authStatus   认证状态
     * @param now          当前时间
     */
    private void insertNewReport(long userId, Integer authCategory, Integer authItem, Integer authStatus, Date now) {
        UserAuthRecord userAuthReport = new UserAuthRecord();
        userAuthReport.setUserId(userId);
        userAuthReport.setAuthCategory(authCategory);
        userAuthReport.setAuthItem(authItem);
        userAuthReport.setStatus(authStatus);
        userAuthReport.setGmtCreated(now);
        if (AuthStatusEnum.VERIFICATION.getCode().equals(authStatus)) {
            userAuthReport.setAuthDetail("认证中");
        } else if (AuthStatusEnum.PASS.getCode().equals(authStatus)) {
            userAuthReport.setAuthNum(1);
            userAuthReport.setAuthDetail("认证完成");
        } else if (AuthStatusEnum.NO_PASS.getCode().equals(authStatus)) {
            userAuthReport.setAuthDetail("认证失败");
        }
        userAuthRecordMapper.insert(userAuthReport);
    }
}
