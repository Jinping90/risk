package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.ConfigAppVersionDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * APP版本配置Mapper
 *
 * @author czl
 */
@Repository
public interface ConfigAppVersionMapper {

    /**
     * 获取app版本信息，下载地址
     *
     * @return ConfigAppVersionDO
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   config_app_version;")
    ConfigAppVersionDO getAppVersion();
}
