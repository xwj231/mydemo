package com.example.demo.simpleDemo;

import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author xuj231
 * @description
 * @date 2019/8/3 17:48
 */
public class MqConsumer {
    private static Logger logger = Logger.getLogger(MqConsumer.class);

    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer_yll");
        consumer.setNamesrvAddr("192.168.2.144:9876");
        try {
            //可订阅多个tag，但是一个消息只能有一个tag *代表全部的Tag
            consumer.subscribe("TopicA", "tagA||tagB");
            
            //消费策略(三种)
            //从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            
            //默认策略，从该队列最尾开始消费，即跳过历史消息
            //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            
            //从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
            //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
            //consumer.setConsumeTimestamp(String.valueOf(System.currentTimeMillis()));
            
            //设置消费模式：广播模式---还有一个负载均衡MessageModel.CLUSTERING
//            consumer.setMessageModel(MessageModel.BROADCASTING);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    Message msg = list.get(0);
                    logger.info("消费消息："+msg.toString());
                    System.out.println("消费消息："+msg.toString());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            logger.info("consumer启动成功");
        } catch (MQClientException e) {
            logger.error("消费者订阅消息失败，error：" + e);
        }
    }
    
}
