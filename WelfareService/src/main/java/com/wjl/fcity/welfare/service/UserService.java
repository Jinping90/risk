package com.wjl.fcity.welfare.service;

import com.wjl.fcity.welfare.dto.RankingDto;
import com.wjl.fcity.welfare.model.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-04-04 18:47
 */
public interface UserService {
    /**
     * 根据用户手机号，获取用户信息
     *
     * @param mobile 手机号
     * @return User
     */
    User findByMobile(String mobile);

    /**
     * 更新用户信息
     *
     * @param user 签到后修改用户信息的对象
     */
    void updateUser(User user);

    /**
     * 根据用户ID 查询用户
     *
     * @param userId 用户ID
     * @return welfare
     */
    User findOne(Long userId);

    /**
     * 更新用户总的财富值
     *
     * @param wealth 此次收取的财富值
     * @param userId 用户userId
     */
    void updateTotalWealth(BigDecimal wealth, Long userId);

    /**
     * 获取全服排行的集合
     *
     * @return 所有用户根据用户拥有的算力降序的排行集合
     */
    List<RankingDto> getAllServiceOrOwnRankingDesc();

}
