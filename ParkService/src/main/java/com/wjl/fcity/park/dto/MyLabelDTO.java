package com.wjl.fcity.park.dto;

import lombok.Data;

/**
 * @author 杨洋
 * @date 2018/4/3
 */
@Data
public class MyLabelDTO {
    /**
     * 标签id
     */
    private Long labelId;
    /**
     * 标签数量
     */
    private Integer labelCount;
    /**
     * 标签内容
     */
    private String content;
    /**
     * 标签分类[1: 购物行为, 2: 信用, 3: 资产, 4: 价值观, 5: 生活方式]
     */
    private Integer category;
}
