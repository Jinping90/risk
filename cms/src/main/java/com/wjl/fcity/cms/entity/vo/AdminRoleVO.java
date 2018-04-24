package com.wjl.fcity.cms.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 黄骏毅
 */
@Data
public class AdminRoleVO {
    /**
     * 主键
     */
    private long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
