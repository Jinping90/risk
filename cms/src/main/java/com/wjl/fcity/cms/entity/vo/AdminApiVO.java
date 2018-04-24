package com.wjl.fcity.cms.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author czl
 */
@Data
public class AdminApiVO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 1:菜单 2:接口
     */
    private Integer type;
    /**
     * 接口
     */
    private String url;
    /**
     * 排序,1最大
     */
    private Integer sort;
    /**
     * 父id
     */
    private Long parentId;

    private List<AdminApiVO> subApis;
}
