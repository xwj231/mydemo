package com.example.demo.configDemo;

import com.example.demo.global.GlobalVariable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RocketMQMessageListener(topic = GlobalVariable.TOPIC, consumerGroup = GlobalVariable.CONSUMERGROUP ,messageModel = MessageModel.CLUSTERING)
public class ImConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        //设置重试次数
        consumer.setMaxReconsumeTimes(3);
        //设置从第一条消息开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = msgs.get(0);
                byte[] body = messageExt.getBody();
                
                String mqMessage = null;
                try {
//                    mqMessage = objectMapper.readValue(body, String.class);
                    logger.info("收到消息:{}",new String(body));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }catch (Exception e){
                    new RuntimeException("消息格式解析错误!");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
    }

    @Override
    public void onMessage(String message) {
        //实现ack机制是不会调用此方法
    }
}
