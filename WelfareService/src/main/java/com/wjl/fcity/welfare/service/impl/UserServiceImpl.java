package com.wjl.fcity.welfare.service.impl;


import com.wjl.fcity.welfare.dto.RankingDto;
import com.wjl.fcity.welfare.mapper.UserMapper;
import com.wjl.fcity.welfare.model.User;
import com.wjl.fcity.welfare.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fy
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * @param mobile 手机号
     * @return 用户对象
     */
    @Override
    public User findByMobile(String mobile) {
        return userMapper.findByMobile(mobile);
    }

    /**
     * @param user 签到后修改用户信息的对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }


    /**
     * @param userId 用户ID
     * @return 用户对象
     */
    @Override
    public User findOne(Long userId) {
        return userMapper.findOne(userId);
    }

    /**
     * @param wealth 此次收取的财富值
     * @param userId 用户userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTotalWealth(BigDecimal wealth, Long userId) {

        Date gmtModified = new Date();
        userMapper.updateTotalWealth(wealth, gmtModified, userId);
    }

    /**
     * 获取全服排行的集合
     *
     * @return 所有用户根据用户拥有的算力降序的排行集合
     */
    @Override
    public List<RankingDto> getAllServiceOrOwnRankingDesc() {
        return userMapper.getAllServiceOrOwnRankingDesc();
    }
}
