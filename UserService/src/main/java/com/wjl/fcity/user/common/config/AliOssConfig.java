package com.wjl.fcity.user.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Fy
 * @implSpec : oos配值设置
 * @date : 2018-03-29 11:26
 */
@Data
@ConfigurationProperties(prefix = "aliOssConfig")
@Component
public class AliOssConfig {

    /**
     * 设备标志.
     */
    private String bucketName;

    /**
     * oos阿里服务器部署地址.
     */
    private String endpoint;

    /**
     * 账号id.
     */
    private String accessKeyId;

    /**
     * 账号密钥.
     */
    private String accessKeySecret;
}
