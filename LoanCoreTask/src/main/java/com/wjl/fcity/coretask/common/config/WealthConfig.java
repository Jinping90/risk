package com.wjl.fcity.coretask.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 水滴生成配置信息
 *
 * @author czl
 */
@Data
@Component
@ConfigurationProperties(prefix = "wealthConfig")
public class WealthConfig {
    /**
     * 生成水滴定间隔小时
     */
    private Integer generateIntervalHours;
}
