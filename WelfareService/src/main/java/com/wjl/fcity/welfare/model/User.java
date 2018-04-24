package com.wjl.fcity.welfare.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author czl
 */
@Data
@Entity
@Table(name = "user")
public class User {

    private static final long serialVersionUID = 2308418084162500432L;
    /**
     * 用户编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
     * 信用值.
     */
    private Integer creditValue;

    /**
     * 用户拥有的财富.
     */
    private BigDecimal totalWealth;

    /**
     * 连续签到天数.
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
     * 邀请人
     */
    private Long inviteUserId;
    /**
     * 注册来源
     */
    private String regSource;
    /**
     * 用户头像
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
