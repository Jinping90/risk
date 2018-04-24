package com.wjl.fcity.coretask.service;


/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-04-03 13:58
 */
public interface CreditValueRecordService {

    /**
     * 新增用户信用值修改记录
     *
     * @param userId                用户ID
     * @param changeCreditValue     修改值
     * @param changeCreditValueEnum 信用值修改类型
     */
    void insertCreditValueRecord(Long userId, Integer changeCreditValue, Integer changeCreditValueEnum);
}
