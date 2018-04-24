package com.wjl.fcity.park.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis基础配置
 *
 * @author czl
 */
@Configuration
@MapperScan("com.wjl.fcity.park.mapper")
public class MyBatisConfig {

}