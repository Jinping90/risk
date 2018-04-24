package com.wjl.fcity.phone.common.enums;

import lombok.Getter;

/**
 * 认证类别
 *
 * @author czl
 */
@Getter
public enum AuthCategoryEnum {

    /**
     * 认证类别
     */
    RESIDENT_CENTER(1, "居民登记中心"),
    BANK_AUTH(2, "银行"),
    PHONE_AUTH(3, "手机营业厅"),
    SHOPPING_CENTRE(4, "购物中心");

    private int code;
    private String name;

    AuthCategoryEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据枚举的code返回枚举对象
     *
     * @param code 枚举code值
     * @return 枚举对象
     */
    public static AuthCategoryEnum getEnumByCode(int code) {
        for (AuthCategoryEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
