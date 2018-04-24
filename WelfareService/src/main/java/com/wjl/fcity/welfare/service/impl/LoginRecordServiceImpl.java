package com.wjl.fcity.welfare.service.impl;

import com.wjl.fcity.welfare.model.LoginRecord;
import com.wjl.fcity.welfare.model.User;
import com.wjl.fcity.welfare.repository.LoginRecordRepository;
import com.wjl.fcity.welfare.service.LoginRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author czl
 */
@Slf4j
@Service
public class LoginRecordServiceImpl implements LoginRecordService {
    @Autowired
    LoginRecordRepository loginRecordRepository;

    @Override
    public void insert(User user, String deviceId) {
        LoginRecord record = new LoginRecord();
        record.setUserId(user.getId());
        record.setDeviceId(deviceId);
        record.setLoginTime(new Date());
        loginRecordRepository.save(record);
    }
}
