package com.wjl.fcity.phone.common.enums;

import lombok.Getter;

/**
 * 第三方类型
 *
 * @author czl
 */
@Getter
public enum ThirdTypeEnum {

    /**
     * 第三方类型
     */
    TONG_DUN(1,"同盾"),
    TENCENT_CLOUD(2,"腾讯云"),
    ONLINE_BANKING(3,"银行卡"),
    CREDIT_EMAIL(4, "信用卡"),
    MOBIL_INFO(5, "运营商"),
    TAO_BAO(6, "淘宝"),
    ALI_PAY(7, "支付宝");

    private Integer code;
    private String msg;

    ThirdTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据枚举的code返回枚举对象
     *
     * @param code 枚举code值
     * @return 枚举对象
     */
    public static ThirdTypeEnum getEnumByCode(int code) {
        for (ThirdTypeEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
