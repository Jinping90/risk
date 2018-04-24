package com.wjl.fcity.bank.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付网关配置信息
 *
 * @author czl
 */
@Data
@Component
@ConfigurationProperties(prefix = "paymentGatewayConfig")
public class PaymentGatewayConfig {
    /**
     * 支付网关ip和端口
     */
    private String paymentGatewayUrl;
    /**
     * 项目类别
     */
    private Integer appId;
}
