package com.wjl.fcity.user.common.enums;

import lombok.Getter;

/**
 * @author : Fy
 * @date : 2018-03-31 15:44
 */
@Getter
public enum FaceVerifyEnum {
    /**
     * 用户人脸识别信息状态
     */
    ADOPT(0, "通过"),
    NOT_THROUGH(1, "不通过");

    private Integer code;
    private String msg;

    FaceVerifyEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
