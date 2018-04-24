package com.wjl.fcity.user.po;

import com.wjl.fcity.user.model.Identity;
import lombok.Data;

/**
 * @author : Fy
 * @implSpec : 商汤人脸识别返回信息
 * @date : 2018-04-01 9:53
 */
@Data
public class LiveIdNumberVerification {

    /**
     * 响应id.
     */
    private String requestId;

    /**
     * 系统响应码.
     */
    private String code;

    /**
     * 得分，值为 0~1，值越大表示是同一个人的可能性越大
     */
    private String verificationScore;
    /**
     * 错误信息
     */
    private String message;
    private String status;
    private Float hackScore;
    private Float verifyScore;
    private String livenessDataId;
    private Identity identity;
    private String imageId;

}
