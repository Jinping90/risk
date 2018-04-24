package com.wjl.fcity.cms.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * 咨询信息
 *
 * @author czl
 */
@Data
public class ArticlesDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 题图 oss key
     */
    private String imageUrl;
    /**
     * 标题
     */
    private String title;
    /**
     * 咨询信息类型 [1:公积金社保....]
     */
    private Integer type;
    /**
     * 是否在banner显示 0.不是 1.是
     */
    private Integer showInBanner;
    /**
     * 是否显示  0.显示 1.不显示
     */
    private Integer showUp;
    /**
     * 阅读人数
     */
    private Integer numberOfReaders;
    /**
     * 编辑人
     */
    private String realName;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
