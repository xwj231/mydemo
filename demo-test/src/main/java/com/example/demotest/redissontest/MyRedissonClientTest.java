package com.example.demotest.redissontest;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author xuwj
 * @date 2021/7/28 14:35
 * @Description
 */
public class MyRedissonClientTest {
    private static final Logger logger = LoggerFactory.getLogger(MyRedissonClientTest.class);
    private static Config config = null;
    static {
        config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.useSingleServer().setPassword("daqsoft2019");
    }
    public static void main(String[] args) {
        RedissonClient client = Redisson.create(config);
        RLock lock1 = client.getLock("lock1");
        try {
            new Thread() {
                @Override
                public void run() {
                    try {
                        logger.info("线程二加锁结果：{}", lock1.tryLock(20, TimeUnit.SECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            TimeUnit.SECONDS.sleep(2);
            new Thread() {
                @Override
                public void run() {
                    logger.info("线程一加锁结果：{}", lock1.tryLock());
                    ;
                }
            }.start();
            // 加锁两次后redis中锁存放内容： hashkey -> lock1 key -> 217d5949-af4a-4a70-bb5a-ba4c7b62a3d8:1  value -> 2
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info("释放锁。。。");
            /*lock1.unlock();
            lock1.unlock();*/
        }
    }
}
