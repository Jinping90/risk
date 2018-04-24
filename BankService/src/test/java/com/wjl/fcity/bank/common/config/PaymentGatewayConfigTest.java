package com.wjl.fcity.bank.common.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class PaymentGatewayConfigTest {

    @Resource
    private PaymentGatewayConfig paymentGatewayConfig;

    @Test
    public void test() {
        log.info("paymentGatewayUrl = {}, appId = {}", paymentGatewayConfig.getPaymentGatewayUrl(), paymentGatewayConfig.getAppId());
    }

}