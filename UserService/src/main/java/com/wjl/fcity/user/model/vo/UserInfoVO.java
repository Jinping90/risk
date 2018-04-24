package com.wjl.fcity.user.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户基本信息
 *
 * @author shengju
 */
@Data
public class UserInfoVO {
    private Long userId;
    /**
     * 性别 0男    1女
     */
    private Integer gender;
    private String mobile;
    /**
     * 财富值
     */
    private String wealthValue;
    /**
     * 信用值
     */
    private String creditValue;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 是否设置过密码
     */
    private Boolean existPassword;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 今天是否签过到
     */
    private Boolean signInToday;
    /**
     * 今天签到获取的信用值
     */
    private Integer signInCreditValue;
}
