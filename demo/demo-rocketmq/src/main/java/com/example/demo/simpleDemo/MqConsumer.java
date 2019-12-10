package com.example.demo.simpleDemo;

import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.UtilAll;
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
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer_yl2");
        consumer.setNamesrvAddr("192.168.2.148:9876");
        try {
            //可订阅多个tag，但是一个消息只能有一个tag *代表全部的Tag
            consumer.subscribe("TopicA", "tagA");

            /**
             *      1.消费策略是对新的customerGroup有效，
             * 以前接收过消息的customerGroup，即使设置了消费策略，
             * 还是从上一条消息开始消费。
             *      2.订阅组(customerGroup)不存在情况下，
             * 如果这个队列的消息最小Offset是0，则表示这个Topic上线时间不长，
             * 服务器堆积的数据也不多，那么这个订阅组就从0开始消费。
             * 
             * 分析：
             * http://www.itkeyword.com/doc/4394593802012908341/rocketMQ
             */
            //消费策略(三种)
            //从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //默认策略，从该队列最尾开始消费，即跳过历史消息
            //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            //从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
            //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
            //consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
            
            //设置消费模式：广播模式MessageModel.BROADCASTIN---还有一个负载均衡MessageModel.CLUSTERING
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    Message msg = list.get(0);
                    logger.info("消费消息："+msg.toString());
                    System.out.println("消费消息："+new String(msg.getBody()));
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
