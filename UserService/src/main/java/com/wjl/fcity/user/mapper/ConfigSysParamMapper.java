package com.wjl.fcity.user.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author xuhaihong
 * @date 2018-04-09 18:07
 **/
@Repository
public interface ConfigSysParamMapper {

    /**
     * 查询系统配置值by key
     * @param paramKey key
     * @return value
     */
    @Select("SELECT param_value from config_sys_param where param_key = #{paramKey}")
    String getSysParamConfig(String paramKey);
}
