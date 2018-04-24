package com.wjl.fcity.user.model;

import lombok.Data;

import java.util.Date;

/**
 * 系统配置表
 *
 * @author xuhaihong
 * @date 2018-04-09 18:04
 **/
@Data
public class ConfigSysParam {

    private Long id;
    /**
     * key
     */
    private String paramKey;
    /**
     * value
     */
    private String paramValue;
    private Date gmtCreated;
    private Date gmtModified;

}
