package com.wjl.fcity.user.mapper.provider;

import com.wjl.fcity.user.UserServiceApplication;
import com.wjl.fcity.user.model.TwoElementAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.wjl.fcity.user.mapper.TwoElementsAuthMapper;

/**
 * @author xuhaihong
 * @date 2018-03-30 15:05
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class TestXhh {

    @Autowired
    TwoElementsAuthMapper twoElementsAuthMapper;

    @Test
    public void test(){

        TwoElementAuth twoElementAuth = new TwoElementAuth();
        twoElementAuth.setIdCardNo("123");
        twoElementAuth.setName("zhangsna");
        twoElementsAuthMapper.getTwoElementAuth(twoElementAuth);

    }

}
