package com.wjl.fcity.park.model;

import lombok.Data;

import java.util.Date;

/**
 * @author 杨洋
 * @date 2018/3/27
 */
@Data
public class Label {
    /**
     * 主键
     */
    private Long id;
    /**
     * 标签分类[1: 购物行为, 2: 信用, 3: 资产, 4: 价值观, 5: 生活方式]
     */
    private Integer category;
    /**
     * 标签内容
     */
    private String content;
    /**
     * 未选中背景颜色
     */
    private String unselectedBackgroundColor;
    /**
     * 选中背景颜色
     */
    private String pitchOnBackgroundColor;
    /**
     * 未选中字体颜色
     */
    private String unselectedFontColor;
    /**
     * 选中字体颜色
     */
    private String pitchOnFontColor;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;

}
