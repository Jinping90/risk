package com.wjl.fcity.coretask.service.impl;

import com.wjl.fcity.coretask.mapper.ConfigAuthParamMapper;
import com.wjl.fcity.coretask.model.ConfigAuthParamDO;
import com.wjl.fcity.coretask.service.ConfigAuthParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 认证有效期配置Service实现
 * @author czl
 */
@Service
public class ConfigAuthParamServiceImpl implements ConfigAuthParamService {

    @Resource
    private ConfigAuthParamMapper configAuthParamMapper;

    @Override
    public List<ConfigAuthParamDO> listHaveValidity() {
        return configAuthParamMapper.listHaveValidity();
    }
}
