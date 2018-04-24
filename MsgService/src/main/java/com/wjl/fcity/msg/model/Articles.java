package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Version;
import java.util.Date;

/**
 * 社区资讯
 *
 * @author xuhaihong
 * @date 2018-03-28 22:58
 **/
@Data
public class Articles {

    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 副文本内容
     */
    private String content;
    /**
     * 咨询信息类型 [1: 公积金社保....]
     */
    private Integer type;
    /**
     * 阅读人数
     */
    private Integer numberOfReaders;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * banner轮播图 oss key 或者图片url路径
     */
    private String imageUrl;
    /**
     * 是否在banner显示 0.是 1.不是
     */
    private Integer showInBanner;
    /**
     * 是否显示  0.显示 1.不显示
     */
    private Integer showUp;


}
