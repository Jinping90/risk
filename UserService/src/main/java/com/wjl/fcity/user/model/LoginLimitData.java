package com.wjl.fcity.user.model;

import lombok.Data;

/**
 * 用于记录登录次数
 *
 * @author shengju
 */
@Data
public class LoginLimitData {

    private Integer count;

    private Long loginTime;

}
