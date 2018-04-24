package com.wjl.fcity.cms.entity.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 咨询信息请求体
 *
 * @author czl
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticlesReq extends PageReq {
    /**
     * 主键
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容,h5富文本 或 oss key
     */
    private String content;
    /**
     * 编辑人
     */
    private Long editor;
    /**
     * 咨询信息类型 [1:公积金社保....]
     */
    private Integer type;
    /**
     * 阅读人数
     */
    private Integer numberOfReaders;
    /**
     * 题图 oss key
     */
    private String imageUrl;
    /**
     * 是否在banner显示 0.不是 1.是
     */
    private Integer showInBanner;
    /**
     * 是否显示  0.显示 1.不显示
     */
    private Integer showUp;
    /**
     * 排序
     */
    private Integer sort;
}
