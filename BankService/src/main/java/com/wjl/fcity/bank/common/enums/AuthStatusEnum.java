package com.wjl.fcity.bank.common.enums;

import lombok.Getter;

/**
 * 认证状态
 *
 * @author czl
 */
@Getter
public enum AuthStatusEnum {

    /**
     * 认证状态
     */
    AUTH_ING(1,"验证中"),
    AUTH_PASS(2,"通过"),
    AUTH_FAIL(3,"不通过"),
    AUTH_OVER(4, "认证过期");

    private Integer code;
    private String msg;

    AuthStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据枚举的code返回枚举对象
     *
     * @param code 枚举code值
     * @return 枚举对象
     */
    public static AuthStatusEnum getEnumByCode(int code) {
        for (AuthStatusEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
