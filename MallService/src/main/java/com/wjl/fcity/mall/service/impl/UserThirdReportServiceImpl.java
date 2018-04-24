package com.wjl.fcity.mall.service.impl;

import com.wjl.fcity.mall.common.enums.ThirdTypeEnum;
import com.wjl.fcity.mall.entity.request.MoXieResultReq;
import com.wjl.fcity.mall.entity.model.UserThirdReportDO;
import com.wjl.fcity.mall.mapper.UserThirdReportMapper;
import com.wjl.fcity.mall.service.UserThirdReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户第三方报告信息业务实现
 *
 * @author czl
 */
@Service
public class UserThirdReportServiceImpl implements UserThirdReportService {

    @Resource
    private UserThirdReportMapper userThirdReportMapper;

    @Override
    public void insert(MoXieResultReq moXieResult, ThirdTypeEnum thirdTypeEnum) {
        Date now = new Date();
        //保存第三方报告信息
        UserThirdReportDO userThirdReportDO = new UserThirdReportDO();
        userThirdReportDO.setUserId(moXieResult.getUserId());
        userThirdReportDO.setThirdType(thirdTypeEnum.getCode());
        userThirdReportDO.setBillId(moXieResult.getBillId());
        userThirdReportDO.setReportId(moXieResult.getReportId());
        userThirdReportDO.setMessage(moXieResult.getMessage());
        userThirdReportDO.setGmtCreated(now);
        userThirdReportMapper.insert(userThirdReportDO);
    }
}
