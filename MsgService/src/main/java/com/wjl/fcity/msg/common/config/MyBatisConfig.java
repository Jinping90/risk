package com.wjl.fcity.msg.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis基础配置
 *
 * @author czl
 */
@Configuration
@MapperScan("com.wjl.fcity.msg.mapper")
public class MyBatisConfig {

}