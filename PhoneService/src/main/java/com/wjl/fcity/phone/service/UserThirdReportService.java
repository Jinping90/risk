package com.wjl.fcity.phone.service;


import com.wjl.fcity.phone.common.enums.ThirdTypeEnum;
import com.wjl.fcity.phone.entity.request.MoXieResultReq;

/**
 * 用户第三方报告信息业务
 *
 * @author czl
 */
public interface UserThirdReportService {

    /**
     * 新增第三方报告信息
     *
     * @param moXieResult 魔蝎请求体
     * @param thirdTypeEnum 第三方类型
     */
    void insert(MoXieResultReq moXieResult, ThirdTypeEnum thirdTypeEnum);
}
