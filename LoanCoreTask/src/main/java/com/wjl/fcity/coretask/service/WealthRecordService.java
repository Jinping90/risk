package com.wjl.fcity.coretask.service;

import com.wjl.fcity.coretask.model.WealthRecordDO;

/**
 * 财富记录业务处理
 *
 * @author czl
 */
public interface WealthRecordService {

    /**
     * 查入新的用户财富记录
     *
     * @param wealthRecordDO 财富记录
     */
    void insert(WealthRecordDO wealthRecordDO);
}
