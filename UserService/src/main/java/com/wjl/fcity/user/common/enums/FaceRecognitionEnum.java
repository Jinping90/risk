package com.wjl.fcity.user.common.enums;

import lombok.Getter;

/**
 * @author : Fy
 * @date : 2018-04-01 13:49
 */
@Getter
public enum FaceRecognitionEnum {
    /**
     * 调用商汤人脸识别信息返回状态
     */
    AUTHENTICATION_THROUGH(1000, "身份认证未通过"),
    NAME_AND_ID_CARD_NOT_MATCH(3003, "姓名与身份证号不匹配"),
    INVALID_IDENTITY_CARD_NUMBER(3004, "姓名与身份证号不匹配"),
    DETECTION_FAILURE(4000, "检测失败"),
    RESOURCES_NOT_FOUND(2000, "云端资源未找到");

    private Integer code;
    private String msg;

    FaceRecognitionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
