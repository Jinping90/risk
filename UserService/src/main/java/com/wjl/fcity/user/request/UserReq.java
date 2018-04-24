package com.wjl.fcity.user.request;

import lombok.Data;

/**
 * 用户请求
 *
 * @author shengju
 */
@Data
public class UserReq {
    private Long userId;

    private String mobile;

    private String username;

    private String password;

    /**
     * 设置密码
     */
    private String beginPassword;
    /**
     * 登入设备Id
     */
    private String deviceId;
    /**
     * 短信验证码
     */
    private String verifyCode;
    /**
     * 新密码
     */
    private String newPassword;
    /**
     * 验证码token
     */
    private String noteToken;
    /**
     * 信用值
     */
    private Integer creditValue;
    /**
     * 注册来源
     */
    private String regSource;
    /**
     * 平台
     */
    private Integer platform;
    private String sessionCode;
}
