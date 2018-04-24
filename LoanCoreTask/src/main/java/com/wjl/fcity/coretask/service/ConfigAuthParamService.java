package com.wjl.fcity.coretask.service;

import com.wjl.fcity.coretask.model.ConfigAuthParamDO;

import java.util.List;

/**
 * 认证有效期配置Service
 *
 * @author czl
 */
public interface ConfigAuthParamService {

    /**
     * 查询有效期大于0的认证配置信息
     *
     * @return List<ConfigAuthParamDO>
     */
    List<ConfigAuthParamDO> listHaveValidity();
}
