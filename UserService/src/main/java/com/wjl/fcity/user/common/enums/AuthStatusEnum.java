package com.wjl.fcity.user.common.enums;

import lombok.Getter;

/**
 * @author Fy
 * 用户身份证信息状态
 */
@Getter
public enum AuthStatusEnum {

    /**
     * 用户认证记录表状态
     */
    WAIT_ACTIVE(0, "未认证"),
    VERIFICATION(1, "验证中"),
    PASS(2, "通过"),
    NO_PASS(3, "不通过"),
    PAST_DUE(4, "认证过期");

    private Integer code;
    private String msg;

    AuthStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
