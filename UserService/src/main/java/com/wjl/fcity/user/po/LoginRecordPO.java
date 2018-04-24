package com.wjl.fcity.user.po;

import lombok.Data;

import java.util.Date;

/**
 * 登入记录表
 *
 * @author shengju
 */
@Data
public class LoginRecordPO {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 登入设备Id
     */
    private String deviceId;
    /**
     * 登入时间
     */
    private Date loginTime;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;


}
