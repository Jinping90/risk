package com.wjl.fcity.welfare.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis基础配置
 *
 * @author czl
 */
@Configuration
@MapperScan("com.wjl.fcity.welfare.mapper")
public class MyBatisConfig {

}