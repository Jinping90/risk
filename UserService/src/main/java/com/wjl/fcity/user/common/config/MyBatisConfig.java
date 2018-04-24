package com.wjl.fcity.user.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis基础配置
 *
 * @author czl
 */
@Configuration
@MapperScan("com.wjl.fcity.user.mapper")
public class MyBatisConfig {

}