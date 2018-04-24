package com.wjl.fcity.phone.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 风控接口配置信息
 *
 * @author czl
 */
@Data
@Component
@ConfigurationProperties(prefix = "riskManagementConfig")
public class RiskManagementConfig {
    /**
     * 项目标识码
     */
    private String identification;
    /**
     * 风控ip和端口
     */
    private String riskIp;
    /**
     * 获取手机运营商报告的url
     */
    private String getModelMobileOperatorUrl;
}
