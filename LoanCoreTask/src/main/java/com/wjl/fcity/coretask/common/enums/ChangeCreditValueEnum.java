package com.wjl.fcity.coretask.common.enums;

import lombok.Getter;

/**
 * 信用值修改类型
 *
 * @author czl
 */
@Getter
public enum ChangeCreditValueEnum {
    /**
     * 信用值修改类型
     */
    SIGN_IN(1, "签到"),
    ID_CARD_FACE(2, "人脸认证"),
    CREDIT_CARD_AUTH(3, "信用卡认证"),
    ONLINE_BANKING(4, "网银认证"),
    CREDIT_EMAIL_AUTH(5, "信用卡邮箱认证"),
    MOBIL_INFO(6, "手机运营商认证"),
    ALI_PAY_AUTH(7, "支付宝认证"),
    TAO_BAO_AUTH(8, "淘宝认证");

    private Integer code;
    private String msg;

    ChangeCreditValueEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据枚举的code返回枚举对象
     *
     * @param code 枚举code值
     * @return 枚举对象
     */
    public static ChangeCreditValueEnum getEnumByCode(int code) {
        for (ChangeCreditValueEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
