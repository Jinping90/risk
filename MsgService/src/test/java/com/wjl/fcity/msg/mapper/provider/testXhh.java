package com.wjl.fcity.msg.mapper.provider;

import com.wjl.fcity.msg.MsgServiceApplication;
import com.wjl.fcity.msg.mapper.MessageStatusMapper;
import com.wjl.fcity.msg.model.MessageStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author xuhaihong
 * @date 2018-04-02 20:07
 **/

@SpringBootTest(classes = MsgServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class testXhh {

    @Autowired
    MessageStatusMapper messageStatusMapper;

    @Test
    public void test001(){

        MessageStatus messageStatus = new MessageStatus();
        Integer hasRead = 1;
        Date date = new Date();
        messageStatus.setMessageId(2L);
        messageStatus.setUserId(1L);
        messageStatus.setStatus(hasRead);
        messageStatus.setGmtModified(date);
        messageStatusMapper.update(messageStatus);
    }
}
