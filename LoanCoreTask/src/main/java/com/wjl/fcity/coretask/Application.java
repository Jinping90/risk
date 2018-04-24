package com.wjl.fcity.coretask;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 核心定时任务服务<br><br>
 * 
 * 
 * 定时任务名称:		xxxxx<br>
 * 定时任务名称Cn:	自动放款任务<br>
 * 执行时机：		系统启动后30m后首次执行，当前任务执行完毕后10*60m后执行下一次任务<br><br><br>
 * 
 * 
 * 定时任务名称:		xxxxx<br>
 * 定时任务名称Cn:	自动扣款任务<br>
 * 执行时机：		每日1点<br>
 * 
 * @author 秦瑞华
 *
 */
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient	/*服务注册与发现*/
@SpringBootApplication
@MapperScan(basePackages = "com.fcity.coretask.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
