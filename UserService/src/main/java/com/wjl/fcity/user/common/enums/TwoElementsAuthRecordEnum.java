package com.wjl.fcity.user.common.enums;

import lombok.Getter;

/**
 * @author : Fy
 * @date : 2018-03-30 17:18
 */
@Getter
public enum TwoElementsAuthRecordEnum {

    /**
     * 二要素认证记录表状态
     */
    CERTIFICATION_SUCCESS("0", "亲,认证成功(收费)"),
    CERTIFICATION_DIFFER("1", "亲，认证信息不一致(收费)"),
    INFORMATION_NOT_EXIST("2", "亲,认证信息不存在(不收费)"),
    CERTIFICATION_SUCCESS_NO_PHOTOS("3", "亲,认证成功无照片(收费)"),
    CERTIFICATION_ABNORMAL("9", "亲,其他异常(不收费), 业务失败错误码: 错误信息(不收费)");

    private String code;
    private String msg;

    TwoElementsAuthRecordEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
