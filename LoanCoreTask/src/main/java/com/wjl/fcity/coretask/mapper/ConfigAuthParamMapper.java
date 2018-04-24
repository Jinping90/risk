package com.wjl.fcity.coretask.mapper;

import com.wjl.fcity.coretask.model.ConfigAuthParamDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 查询有效期大于0的认证配置信息
     *
     * @return List<ConfigAuthParamDO>
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   config_auth_param " +
            "WHERE " +
            "   validity_period > 0;")
    List<ConfigAuthParamDO> listHaveValidity();
}
