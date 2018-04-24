package com.wjl.fcity.user.model;

import lombok.Data;

import java.util.Date;

/**
 * APP版本配置表
 *
 * @author czl
 */
@Data
public class ConfigAppVersionDO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 当前最新版本号（安卓）
     */
    private String androidCurrent;
    /**
     * 最低版本号（安卓）
     */
    private String androidLimit;
    /**
     * 安卓更新下载地址
     */
    private String androidUrl;
    /**
     * 当前最新版本号（IOS）
     */
    private String iosCurrent;
    /**
     * 最低版本号（IOS）
     */
    private String iosLimit;
    /**
     * IOS更新下载地址
     */
    private String iosUrl;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
}
