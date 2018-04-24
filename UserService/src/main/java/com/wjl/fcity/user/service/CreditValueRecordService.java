package com.wjl.fcity.user.service;


import com.wjl.fcity.user.model.CreditValueRecord;

import java.util.List;

/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-04-03 13:58
 */
public interface CreditValueRecordService {

    /**
     * 用户信用值新增都会增加新的记录
     *
     * @param creditValueRecord 用户信用值记录表
     */
    void insertCreditValueRecord(CreditValueRecord creditValueRecord);

    /**
     * 新增用户信用值修改记录
     *
     * @param userId                用户ID
     * @param changeCreditValue     修改值
     * @param changeCreditValueEnum 信用值修改类型
     */
    void insertCreditValueRecord(Long userId, Integer changeCreditValue, Integer changeCreditValueEnum);

    /**
     * 查询信用值增加记录
     * @param userId 用户ID
     * @param type 修改类型
     * @return List<CreditValueRecord>
     */
    List<CreditValueRecord> listByUserIdAndType(Long userId, Integer type);
}
