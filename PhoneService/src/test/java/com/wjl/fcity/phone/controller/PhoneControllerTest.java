package com.wjl.fcity.phone.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PhoneControllerTest {

    @Resource
    private PhoneController phoneController;

    @Test
    public void getPhoneReport() {
        System.out.println(phoneController.getPhoneReport(null));
    }
}