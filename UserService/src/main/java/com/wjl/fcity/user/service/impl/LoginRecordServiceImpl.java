package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.po.LoginRecordPO;
import com.wjl.fcity.user.mapper.LoginRecordMapper;
import com.wjl.fcity.user.service.LoginRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author czl
 */
@Slf4j
@Service
public class LoginRecordServiceImpl implements LoginRecordService {

    @Resource
    private LoginRecordMapper loginRecordMapper;


    @Override
    public void addLoginRecord(LoginRecordPO loginRecordPO) {
        loginRecordMapper.addLoginRecord(loginRecordPO);
    }
}
