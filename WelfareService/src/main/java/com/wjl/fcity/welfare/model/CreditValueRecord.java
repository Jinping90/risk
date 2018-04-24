package com.wjl.fcity.welfare.model;

import lombok.Data;

import java.util.Date;

/**
 * @author : Fy
 * @implSpec : 用户信用值记录表
 * @date : 2018-04-03 11:13
 */
@Data
public class CreditValueRecord {
    /**
     * 主键id.
     */
    private Integer id;

    /**
     * 用户id.
     */
    private Long userId;

    /**
     * 信用值变化值.
     */
    private Integer changeCreditValue;

    /**
     * 修改类型 [1: 签到, 2: 人脸认证, 3:
     * 手机运营商认证, 4: 信用卡认证, 5: 网银认证,
     * 6: 淘宝认证, 7: 信用卡邮箱认证, 8: 支付宝认证]
     */
    private Integer type;

    /**
     * 创建时间.
     */
    private Date gmtCreated;

    /**
     * 修改时间.
     */
    private Date gmtModified;

}
