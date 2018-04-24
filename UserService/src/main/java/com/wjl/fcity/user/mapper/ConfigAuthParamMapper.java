package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.ConfigAuthParamDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 认证有效期配置
 *
 * @author czl
 */
@Repository
public interface ConfigAuthParamMapper {

    /**
     * 根据认证项目
     *
     * @param authItem 认证项目
     * @return ConfigAuthParam
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   config_auth_param " +
            "WHERE " +
            "   auth_item = #{authItem} " +
            "LIMIT 1;")
    ConfigAuthParamDO findByAuthItem(@Param("authItem") Integer authItem);
}
