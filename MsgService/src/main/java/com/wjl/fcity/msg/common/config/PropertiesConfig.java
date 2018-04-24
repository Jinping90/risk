package com.wjl.fcity.msg.common.config;

import com.wjl.fcity.msg.common.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Properties配置
 * 1.来自Spring Cloud Config, 本例既是如此，这样可以集中配置，最大化减少本地配置，目的是不同环境不需要修改本地配置文件
 * 2.来自.properties/.yml文件
 * 使用方式：使用@Autowired注入
 * <p>
 * Properties与@value都能获取以上2个来源的配置信息，按实际需要使用
 * .properties的属性值可以互相引用，因此推荐的做法是
 *
 * @author 杨洋
 */
@EnableConfigurationProperties({AppProperties.class})
@Configuration
public class PropertiesConfig {

}
