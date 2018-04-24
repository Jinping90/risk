package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.ConfigAppVersionDO;

/**
 * APP版本配置Service
 *
 * @author czl
 */
public interface ConfigAppVersionService {

    /**
     * 获取app版本信息，下载地址
     *
     * @return ConfigAppVersionDO
     */
    ConfigAppVersionDO getAppVersion();
}
