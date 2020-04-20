package com.example.demozk.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

/**
 * @author xuj231
 * @description
 * @date 2019/9/10 16:29
 */
public class DistributedAtomicIntegerTest {

    static CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) throws Exception {
        CuratorFramework zkClient = getZkClient();
        //指定计数器存放路径 及重试策略
        DistributedAtomicInteger distributedAtomicInteger = new DistributedAtomicInteger(zkClient, "/counter", new ExponentialBackoffRetry(1000, 3));
        distributedAtomicInteger.trySet(0);
        System.out.println("初始值："+distributedAtomicInteger.get().postValue());
        //多线程自增10*100次
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    try {
                        //调用add方法自增
                        AtomicValue<Integer> result = distributedAtomicInteger.add(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                countDownLatch.countDown();

            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //查看结果
        System.out.println("多线程自增结果" + distributedAtomicInteger.get().postValue());
    }

    private static CuratorFramework getZkClient() {
        String zkServerAddress = "192.168.2.110:2181";
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3, 5000);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(zkServerAddress)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }
    
}
