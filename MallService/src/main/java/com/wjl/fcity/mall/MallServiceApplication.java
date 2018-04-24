package com.wjl.fcity.mall;

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
 * 《APP后端》Phone微服务<br><br>
 * <p>
 * 测试环境地址：http://183.129.157.218:2000/Api-App/
 * 生产环境地址：http://106.15.129.51/Api-App/
 * <p>
 * <p>
 * APP的API设计规范
 * 1.以“/Api-App/”路由到APP的服务端App-Service，需要授权的api会由API网关校验token
 * 2.需要授权的api需要设计为以“/auth/” 开头,如申请请求："/auth/apply"，此时API网关会校验token
 * 3.其他的请求为安全请求,如登录请求"/login"，此时API网关不会校验token
 *
 * @author 杨洋
 */
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class MallServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallServiceApplication.class, args);
    }
}
