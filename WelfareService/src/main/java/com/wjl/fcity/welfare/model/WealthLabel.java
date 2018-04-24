package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * wealth标签类型表
 *
 * @author  fly
 */
@Data
@Entity
@Table(name = "wealth_label")
public class WealthLabel {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 标签分类
     */
    private Integer category;
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
     * 标签内容
     */
    private String content;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
