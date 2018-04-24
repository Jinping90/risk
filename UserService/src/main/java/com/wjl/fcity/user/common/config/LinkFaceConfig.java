package com.wjl.fcity.user.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Fy
 * 参数定义
 * @date : 2018-03-31 18:32
 */
@Data
@ConfigurationProperties(prefix = "linkFaceConfig")
@Component
public class LinkFaceConfig {

    /**
     * api的id.
     */
    private String apiId;
    /**
     * apiSecret的密钥
     */
    private String apiSecret;
    /**
     * 公安部照片与人脸图片比对的url.
     */
    private String selfieIdNumberVerification;
    /**
     * 人脸识别对比的url
     */
    private String livenessSelfieVerification;
}
