package com.example.demo.configDemo;

import com.example.demo.global.GlobalVariable;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ImProvider {
    

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult sendMsg(String msg) {
        return rocketMQTemplate.syncSend(GlobalVariable.TOPIC,msg);
    }

}
