package com.wjl.fcity.user.common.enums;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

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
    ID_CARD_FACE(3, "人脸认证", AuthCategoryEnum.RESIDENT_REGISTRATION_CENTRE),
    CREDIT_CARD_AUTH(4, "信用卡认证", AuthCategoryEnum.BANK),
    ONLINE_BANKING(5, "网银认证", AuthCategoryEnum.BANK),
    CREDIT_EMAIL_AUTH(6, "信用卡邮箱认证", AuthCategoryEnum.BANK),
    MOBIL_INFO(7, "手机运营商认证", AuthCategoryEnum.MOBILE_PHONE_BOOTH),
    ALI_PAY_AUTH(8, "支付宝认证", AuthCategoryEnum.SHOPPING_CENTER),
    TAO_BAO_AUTH(9, "淘宝认证", AuthCategoryEnum.SHOPPING_CENTER);

    /**
     * 认证编号
     */
    private int code;
    /**
     * 认证名称
     */
    private String name;
    /**
     * 所属认证类别
     */
    private AuthCategoryEnum authCategory;

    AuthItemEnum(int code, String name, AuthCategoryEnum authCategory) {
        this.code = code;
        this.name = name;
        this.authCategory = authCategory;
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

    /**
     * 根据认证类别返回编号集合
     *
     * @param authCategory 认证类别
     * @return 编号集合
     */
    public static List<Integer> listCodeByAuthCategory(AuthCategoryEnum authCategory) {
        List<Integer> authItemList = Lists.newArrayList();
        for (AuthItemEnum type : values()) {
            if (authCategory.equals(type.getAuthCategory())) {
                authItemList.add(type.code);
            }
        }
        return authItemList;
    }
}
