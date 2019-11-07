package com.example.demo;

import com.example.demo.configDemo.ImProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoRocketmqApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ImProvider imProvider;

    public static void main(String[] args) {
        SpringApplication.run(DemoRocketmqApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("开始发送消息");
        for (int i=0;i<20;i++) {
            imProvider.sendMsg("mq测试消息："+(i+1));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("消息发送结束");
    }
}
