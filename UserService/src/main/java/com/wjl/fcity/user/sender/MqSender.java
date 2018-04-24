package com.wjl.fcity.user.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * vo
 * @author shengju
 */
@Component  
public class MqSender {  
  
    @Resource
    private RabbitTemplate rabbitTemplate;  
  
    public void send(String queueName, Object msg) {
        this.rabbitTemplate.convertAndSend(queueName, msg);  
    }  
}  
