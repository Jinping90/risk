package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.mapper.ConfigAppVersionMapper;
import com.wjl.fcity.user.model.ConfigAppVersionDO;
import com.wjl.fcity.user.service.ConfigAppVersionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * APP版本配置Service实现
 *
 * @author czl
 */
@Service
public class ConfigAppVersionServiceImpl implements ConfigAppVersionService {

    @Resource
    private ConfigAppVersionMapper configAppVersionMapper;

    @Override
    public ConfigAppVersionDO getAppVersion() {
        return configAppVersionMapper.getAppVersion();
    }
}
