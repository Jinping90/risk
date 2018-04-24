package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.mapper.CreditValueRecordMapper;
import com.wjl.fcity.user.model.CreditValueRecord;
import com.wjl.fcity.user.model.CreditValueRecordDO;
import com.wjl.fcity.user.service.CreditValueRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<CreditValueRecord> listByUserIdAndType(Long userId, Integer type) {
        return creditValueRecordMapper.listByUserIdAndType(userId, type);
    }

    /**
     * 根据用户生的信用值插入到用户信用值记录表中
     *
     * @param creditValueRecord 用户信用值记录表对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCreditValueRecord(CreditValueRecord creditValueRecord) {
        creditValueRecordMapper.insertCreditValueRecordToSign(creditValueRecord);
    }
}
