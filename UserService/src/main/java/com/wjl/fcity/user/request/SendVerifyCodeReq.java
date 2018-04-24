package com.wjl.fcity.user.request;

import lombok.Data;

/**
 * 发送验证码vo
 * @author shengju
 */
@Data
public class SendVerifyCodeReq {
    private String mobile;
    private Integer type;
}
