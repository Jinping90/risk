package com.wjl.fcity.phone.service.impl;


import com.wjl.fcity.phone.common.enums.*;
import com.wjl.fcity.phone.entity.request.AuthRecordReq;
import com.wjl.fcity.phone.entity.request.CreditValueReq;
import com.wjl.fcity.phone.entity.request.MoXieResultReq;
import com.wjl.fcity.phone.service.UserAuthRecordService;
import com.wjl.fcity.phone.service.UserThirdReportService;
import com.wjl.fcity.phone.service.micro.service.UserMicroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 认证纪录业务实现
 *
 * @author czl
 */
@Slf4j
@Service
public class UserAuthRecordServiceImpl implements UserAuthRecordService {

    @Resource
    private UserThirdReportService userThirdReportService;
    @Resource
    private UserMicroService userMicroService;

    /**
     * 处理认证回调
     *
     * @param moXieResult  魔蝎信息
     * @param authCategory 认证类别
     * @param authItem     认证项目
     * @param thirdType    第三方类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealWithAuthCallback(MoXieResultReq moXieResult, AuthCategoryEnum authCategory, AuthItemEnum authItem,
                                     ThirdTypeEnum thirdType, ChangeCreditValueEnum changeCreditValueEnum) {
        AuthRecordReq authRecordReq = new AuthRecordReq(moXieResult.getUserId(), authCategory, authItem, AuthStatusEnum.AUTH_ING);

        switch (moXieResult.getCode()) {
            case "3":
                //将认证状态修改为查询中
                userMicroService.saveOrUpdateAuthRecord(authRecordReq);
                break;
            case "2":
                //将认证状态修改为失败
                authRecordReq.setAuthStatus(AuthStatusEnum.AUTH_FAIL.getCode());
                userMicroService.saveOrUpdateAuthRecord(authRecordReq);
                break;
            case "1":
                //将认证状态修改为成功
                authRecordReq.setAuthStatus(AuthStatusEnum.AUTH_PASS.getCode());
                boolean isSaveSuccess = (boolean) userMicroService.saveOrUpdateAuthRecord(authRecordReq).getData();
                if (isSaveSuccess) {
                    //保存第三方报告信息
                    userThirdReportService.insert(moXieResult, thirdType);
                    //修改用户的信用值
                    CreditValueReq creditValueReq = new CreditValueReq(moXieResult.getUserId(), authItem, changeCreditValueEnum);
                    userMicroService.updateCreditValue(creditValueReq);
                }
                break;
            default:
                log.error("{}回调异常，code={}，userId={}", authItem.getName(), moXieResult.getCode(), moXieResult.getUserId());
                break;
        }
    }
}
