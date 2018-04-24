package com.wjl.fcity.mall.common.enums;

import lombok.Getter;


/**
 * 认证项目
 *
 * @author czl
 */
@Getter
public enum AuthItemEnum {

    /**
     * 认证项目
     */
    REAL_NAME(1, "实名认证"),
    ID_CARD_PHOTO(2, "身份证照片"),
    ID_CARD_FACE(3, "人脸认证"),
    CREDIT_CARD_AUTH(4, "信用卡认证"),
    ONLINE_BANKING(5, "网银认证"),
    CREDIT_EMAIL_AUTH(6, "信用卡邮箱认证"),
    MOBIL_INFO(7, "手机运营商认证"),
    ALI_PAY_AUTH(8, "支付宝认证"),
    TAO_BAO_AUTH(9, "淘宝认证");

    private int code;
    private String name;

    AuthItemEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据枚举的code返回枚举对象
     *
     * @param code 枚举code值
     * @return 枚举对象
     */
    public static AuthItemEnum getEnumByCode(int code) {
        for (AuthItemEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
