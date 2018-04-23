package com.wjl.fcity.loan.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * 本应用的自定义参数
 * 
 * @author 秦瑞华
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "appConfig")
public class AppProperties {
	private String appName;
	private String appNameCn;

	/**
	 * Token加解密秘钥
	 */
	private String tokenKey;
	/**
	 * Token有效期，单位：天
	 */
	private Integer tokenDay;
	/**
	 * token过期时间(分)
	 */
	private Integer tokenDueTime;
}

