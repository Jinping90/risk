package com.wjl.fcity.coretask.common.enums;

import lombok.Getter;

/**
 * @author : Fy
 * @date : 2018-03-30 9:35
 */
@Getter
public enum AuthItemEnum {

    /**
     * 认证项目
     */
    REAL_NAME(1, "实名认证", null),
    ID_CARD_PHOTO(2, "身份证照片", null),
    ID_CARD_FACE(3, "人脸认证", ChangeCreditValueEnum.ID_CARD_FACE),
    CREDIT_CARD_AUTH(4, "信用卡认证", ChangeCreditValueEnum.CREDIT_CARD_AUTH),
    ONLINE_BANKING(5, "网银认证", ChangeCreditValueEnum.ONLINE_BANKING),
    CREDIT_EMAIL_AUTH(6, "信用卡邮箱认证", ChangeCreditValueEnum.CREDIT_EMAIL_AUTH),
    MOBIL_INFO(7, "手机运营商认证", ChangeCreditValueEnum.MOBIL_INFO),
    ALI_PAY_AUTH(8, "支付宝认证", ChangeCreditValueEnum.ALI_PAY_AUTH),
    TAO_BAO_AUTH(9, "淘宝认证", ChangeCreditValueEnum.TAO_BAO_AUTH);

    private int code;
    private String name;
    private ChangeCreditValueEnum changeCreditValueEnum;

    AuthItemEnum(int code, String name, ChangeCreditValueEnum changeCreditValueEnum) {
        this.code = code;
        this.name = name;
        this.changeCreditValueEnum = changeCreditValueEnum;
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
