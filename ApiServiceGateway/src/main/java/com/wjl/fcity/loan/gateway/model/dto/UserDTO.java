package com.wjl.fcity.loan.gateway.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author czl
 */
@Data
public class UserDTO {
    /**
     * 用户编号
     */
    private Long id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 信用值
     */
    private Integer creditValue;
    /**
     * 用户拥有的财富
     */
    private BigDecimal totalWealth;
    /**
     * 连续签到天数
     */
    private Integer signInDays;
    /**
     * 状态:0:初始,1:待审核；2：未通过；3:审核通过.4:黑名单
     */
    private Integer status;
    /**
     * 信用评级
     */
    private Integer creditLevel;

    /**
     * 邀请人id
     */
    private BigInteger inviteUserId;
    /**
     * 注册来源
     */
    private String regSource;
    /**
     * 用户头像,oss_key
     */
    private String profilePhoto;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 更新时间
     */
    private Date gmtModified;
}
