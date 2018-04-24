package com.wjl.fcity.cms.entity.model;

import com.wjl.fcity.cms.entity.dto.AdminRoleApiDto;
import com.wjl.fcity.cms.entity.dto.AdminRoleMenuDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author czl
 */
@Data
public class AdminRole {
    /**
     * 主键
     */
    private long id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 菜单集合
     */
    private List<AdminRoleMenuDto> roleMenus;
    /**
     * 接口集合
     */
    private List<AdminRoleApiDto> roleApis;
    /**
     * 菜单
     */
    private String menus;
    /**
     * 接口
     */
    private String apis;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;

}
