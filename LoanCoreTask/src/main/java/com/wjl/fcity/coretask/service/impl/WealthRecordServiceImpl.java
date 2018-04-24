package com.wjl.fcity.coretask.service.impl;

import com.wjl.fcity.coretask.mapper.WealthRecordMapper;
import com.wjl.fcity.coretask.model.WealthRecordDO;
import com.wjl.fcity.coretask.service.WealthRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 财富记录业务处理实现
 *
 * @author czl
 */
@Service
public class WealthRecordServiceImpl implements WealthRecordService {

    @Resource
    private WealthRecordMapper wealthRecordMapper;

    /**
     * 查入新的用户财富记录
     *
     * @param wealthRecordDO 财富记录
     */
    @Override
    public void insert(WealthRecordDO wealthRecordDO) {
        wealthRecordMapper.insert(wealthRecordDO);
    }
}
