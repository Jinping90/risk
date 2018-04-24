package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author czl
 */
@Data
@Entity
@Table(name = "msg")
public class User {
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
     * 请求token
     */
    private String token;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 注册时间
     */
    private Date regTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
