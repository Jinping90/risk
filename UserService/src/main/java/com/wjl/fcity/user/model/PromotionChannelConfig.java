package com.wjl.fcity.user.model;

import lombok.Data;

import java.util.Date;

/**
 * 推广渠道表
 *
 * @author xuhaihong
 * @date 2018-04-02 15:12
 **/
@Data
public class PromotionChannelConfig {

    private Long id;
    /**
     * 渠道code
     */
    private String channelCode;
    /**
     * 渠道名称
     */
    private String channelName;
    private Date gmtCreated;
    private Date gmtModified;
    /**
     * 外链地址
     */
    private String outsideUrl;
    /**
     * 父渠道ID
     */
    private Long parentId;
    /**
     * 渠道标识（应用市场为0；外链为1）
     */
    private Long channelType;

}
