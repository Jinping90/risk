package com.wjl.fcity.phone.service;


import com.wjl.fcity.phone.common.enums.AuthCategoryEnum;
import com.wjl.fcity.phone.common.enums.AuthItemEnum;
import com.wjl.fcity.phone.common.enums.ChangeCreditValueEnum;
import com.wjl.fcity.phone.common.enums.ThirdTypeEnum;
import com.wjl.fcity.phone.entity.request.MoXieResultReq;

/**
 * 认证纪录业务
 *
 * @author czl
 */
public interface UserAuthRecordService {

    /**
     * 处理认证回调
     *
     * @param moXieResult           魔蝎信息
     * @param authCategory          认证类别
     * @param authItem              认证项目
     * @param thirdType             第三方类型
     * @param changeCreditValueEnum 信用值修改类型
     */
    void dealWithAuthCallback(MoXieResultReq moXieResult, AuthCategoryEnum authCategory, AuthItemEnum authItem,
                              ThirdTypeEnum thirdType, ChangeCreditValueEnum changeCreditValueEnum);
}