package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.PromotionChannelConfig;
import com.wjl.fcity.user.model.PromotionUser;
import com.wjl.fcity.user.request.UserReq;

import java.util.Map;

/**
 * 推广
 *
 * @author xuhaihong
 * @date 2018-04-03 11:07
 **/
public interface PromotionUserService {

    /**
     * 保存
     *
     * @param promotionUser 参数
     * @return id
     */
    Integer insert(PromotionUser promotionUser);

    /**
     * 更新
     *
     * @param promotionUser 参数
     */
    void update(PromotionUser promotionUser);

    /**
     * 查询配置By Id
     *
     * @param id 渠道id
     * @return 渠道信息
     */
    PromotionChannelConfig getPromotionChannelConfig(Long id);

    /**
     * 注册
     *
     * @param user      user
     * @param ip        ip
     * @param id        promotion_user 表 id
     * @param uid       推广id
     * @return 结果
     */
    Map register(UserReq user, String ip, String id, String uid);
}
