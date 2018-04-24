package com.wjl.fcity.coretask.service.impl;

import com.wjl.fcity.coretask.mapper.CreditValueRecordMapper;
import com.wjl.fcity.coretask.model.CreditValueRecordDO;
import com.wjl.fcity.coretask.service.CreditValueRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户信用值记录业务实现
 *
 * @author czl
 */
@Service
public class CreditValueRecordServiceImpl implements CreditValueRecordService {

    @Resource
    private CreditValueRecordMapper creditValueRecordMapper;

    /**
     * 新增用户信用值修改记录
     *
     * @param userId                用户ID
     * @param changeCreditValue     修改值
     * @param changeCreditValueEnum 信用值修改类型
     */
    @Override
    public void insertCreditValueRecord(Long userId, Integer changeCreditValue, Integer changeCreditValueEnum) {
        CreditValueRecordDO creditValueRecordDO = new CreditValueRecordDO();
        creditValueRecordDO.setUserId(userId);
        creditValueRecordDO.setChangeCreditValue(changeCreditValue);
        creditValueRecordDO.setType(changeCreditValueEnum);
        creditValueRecordDO.setGmtCreated(new Date());
        creditValueRecordMapper.insert(creditValueRecordDO);
    }
}
