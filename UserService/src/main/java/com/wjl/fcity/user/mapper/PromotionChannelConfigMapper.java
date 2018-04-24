package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.PromotionChannelConfig;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 渠道配置文件
 * @author xuhaihong
 * @date 2018-04-03 11:36
 **/
@Repository
public interface PromotionChannelConfigMapper {

    /**
     * 查询配置By Id
     * @param id 渠道id
     * @return 渠道信息
     */
    @Select("SELECT * from promotion_channel_config  where id = #{id}")
    PromotionChannelConfig getPromotionChannelConfig(Long id);

}
