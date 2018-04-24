package com.wjl.fcity.welfare.service;
import com.wjl.fcity.welfare.model.User;
import com.wjl.fcity.welfare.model.WealthRecord;

import java.util.List;
import java.util.Map;


/**
 * @author : Fy:
 * @date : 2018-04-04 18:49
 */
public interface WelfareService {
    /**
     * 用户签到
     *
     * @param userId 用户的userId
     * @return 签到后随机增加的信用值（0~3之间）
     */
    Integer signIn(Long userId);


    /**
     * 收取货币的接口
     *
     * @param userId    用户的userId
     * @param welfareId 收取的该货币的id
     * @return 是否已经收取
     */
    boolean gather(Long userId, Long welfareId);


    /**
     * 更新该用户72小时未收取的财富为过期
     *
     * @param userId 用户的userId
     */
    void updateOverdueWealth(Long userId);

    /**
     * 获取该用户在该状态下的财富值集合
     *
     * @param userId 用户的UserId
     * @param status 状态[0: 未收取, 1: 已收取, 2: 已过期]
     * @param page   当前页
     * @param size   每页数
     * @return 用户在该状态下的财富值集合
     */
    List<WealthRecord> getAllGatherWealth(Long userId, Integer status, String page, String size);

    /**
     * 查询该用户在该状态status的总数
     *
     * @param userId 用户的userId
     * @param status 状态[0: 未收取, 1: 已收取, 2: 已过期]
     * @return 统计总数
     */
    int getAllGatherWealthNum(Long userId, Integer status);


    /**
     * 获取全服或是个人的排行榜
     *
     * @param user 用户的对象
     * @param type 0:表示全服排行 1：个人用户算力排行（size为空）
     * @param size 全服前多少名的排行
     * @return Map 返回页面封装的数据
     */
    Map<String, Object> getAllServiceOrOwnRanking(User user, String type, String size);

}
