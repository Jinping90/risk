package com.wjl.fcity.user.model;

import lombok.Data;

import java.util.Date;

/**
 * 推广用户表
 * @author xuhaihong
 * @date 2018-04-02 15:22
 **/
@Data
public class PromotionUser {

    private Long id;
    private String ip;
    private Long userId;
    /**
     * 设备类型 Android  IOS
     */
    private String deviceType;
    /**
     * 渠道ID
     */
    private Long channelId;
    /**
     * 访问时间
     */
    private Date visitDate;
    /**
     * 注册时间
     */
    private Date registDate;

}
