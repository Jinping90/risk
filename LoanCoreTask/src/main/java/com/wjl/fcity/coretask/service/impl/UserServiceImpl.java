package com.wjl.fcity.coretask.service.impl;

import com.wjl.fcity.coretask.common.util.StringUtil;
import com.wjl.fcity.coretask.mapper.UserMapper;
import com.wjl.fcity.coretask.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 用户Service
 *
 * @author czl
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserAuthRecordStatus(Integer creditValue, List<Long> userIdList) {

        String userIdListStr = StringUtil.listToString(userIdList);

        userMapper.updateUserAuthRecordStatus(creditValue, userIdListStr);
    }
}
