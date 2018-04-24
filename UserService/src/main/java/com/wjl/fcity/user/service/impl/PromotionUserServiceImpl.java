package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.common.util.StringUtil;
import com.wjl.fcity.user.mapper.PromotionChannelConfigMapper;
import com.wjl.fcity.user.mapper.PromotionUserMapper;
import com.wjl.fcity.user.model.PromotionChannelConfig;
import com.wjl.fcity.user.model.PromotionUser;
import com.wjl.fcity.user.request.UserReq;
import com.wjl.fcity.user.service.PromotionUserService;
import com.wjl.fcity.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author xuhaihong
 * @date 2018-04-03 11:08
 **/
@Service
@Slf4j
public class PromotionUserServiceImpl implements PromotionUserService {

    @Resource
    private PromotionUserMapper promotionUserMapper;
    @Resource
    private UserService userService;
    @Resource
    private PromotionChannelConfigMapper promotionChannelConfigMapper;

    /**
     * 保存
     *
     * @param promotionUser 参数
     * @return id
     */
    @Override
    public Integer insert(PromotionUser promotionUser) {

        return promotionUserMapper.insert(promotionUser);
    }

    /**
     * 更新
     *
     * @param promotionUser 参数
     */
    @Override
    public void update(PromotionUser promotionUser) {
        promotionUserMapper.update(promotionUser);
    }


    /**
     * 查询配置By Id
     *
     * @param id 渠道id
     * @return 渠道信息
     */
    @Override
    public PromotionChannelConfig getPromotionChannelConfig(Long id) {
        return promotionChannelConfigMapper.getPromotionChannelConfig(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map register(UserReq user, String ip, String id, String uid) {

        Long inviteUserId = Long.valueOf(uid.substring(0, uid.length() - 4));

        //调用用户注册的接口(返回userId和token的数据 )
        Map map = userService.register(user, ip);
        //更新邀请人和推广表
        PromotionUser promotionUser = new PromotionUser();
        promotionUser.setId(Long.valueOf(id));
        promotionUser.setRegistDate(new Date());
        if (StringUtil.isBlank(uid)) {
            log.error("uid为空  更新用户推荐人失败");
        } else {
            userService.updateInviteUserId(inviteUserId, user.getMobile(), promotionUser);
        }
        return map;
    }
}
