package com.wjl.fcity.welfare.common.enums;

import lombok.Getter;

/**
 * @author : Fy
 * @implSpec : 用户信用值记录表类型
 * @date : 2018-04-03 11:01
 */
@Getter
public enum CreditValueRecordEnum {
    /**
     * 用户信用值记录表类型
     * 修改类型 [1: 签到, 2: 人脸认证, 3: 手机运营商认证,
     * 4: 信用卡认证, 5: 网银认证, 6: 淘宝认证,
     * 7: 信用卡邮箱认证, 8: 支付宝认证]
     */
    SIGN(1, "签到"),
    FACE_AUTHENTICATION(2, "人脸认证"),
    MOBILE_OPERATOR_CERTIFICATION(3, "手机运营商认证"),
    CREDIT_CARD_CERTIFICATION(4, "信用卡认证"),
    ONLINE_BANKING_CERTIFICATION(5, "网银认证"),
    TAO_BAO_CERTIFICATION(6, "淘宝认证  "),
    CREDIT_CARD_MAILBOX_CERTIFICATION(7, "信用卡邮箱认证"),
    ALI_PAY_CERTIFIED(8, "信用卡邮箱认证");

    private Integer status;
    private String msg;

    CreditValueRecordEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
