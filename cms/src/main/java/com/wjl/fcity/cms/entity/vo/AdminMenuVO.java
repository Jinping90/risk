package com.wjl.fcity.cms.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author czl
 */
@Data
public class AdminMenuVO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 1:菜单 2:页面
     */
    private Integer type;
    /**
     * 链接
     */
    private String url;
    /**
     * icon
     */
    private String icon;
    /**
     * 排序,1最大
     */
    private Integer sort;
    /**
     * 父id
     */
    private Long parentId;

    private List<AdminMenuVO> subMenus;
}
