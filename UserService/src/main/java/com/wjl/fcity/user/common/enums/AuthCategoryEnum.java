package com.wjl.fcity.user.common.enums;

import lombok.Getter;

/**
 * @author fy
 */
@Getter
public enum AuthCategoryEnum {
    /**
     * 认证类别.
     */
    RESIDENT_REGISTRATION_CENTRE(1, "居民登记中心"),
    BANK(2, "银行"),
    MOBILE_PHONE_BOOTH(3, "手机营业厅"),
    SHOPPING_CENTER(4, "购物中心");

    private Integer code;
    private String msg;

    AuthCategoryEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据枚举的code返回枚举对象
     *
     * @param code 枚举code值
     * @return 枚举对象
     */
    public static AuthCategoryEnum getEnumByCode(Integer code) {
        for (AuthCategoryEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
