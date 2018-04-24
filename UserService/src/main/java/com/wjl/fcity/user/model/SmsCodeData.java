package com.wjl.fcity.user.model;

import lombok.Data;

/**
 * 用于加解密验证码token
 *
 * @author shengju
 */
@Data
public class SmsCodeData {

    private String phone;

    private String vCode;

    private Long time;

    private Integer flag;
}
