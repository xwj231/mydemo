package com.example.demotest.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author xuwj
 * @date 2021/8/27 11:37
 * @Description java并发
 */
public class CallableAndFutureTest {
    public static final Logger logger = LoggerFactory.getLogger(CallableAndFutureTest.class);

    public static void main(String[] args) {
//        logger.info("获取单个线程返回结果：{}", test1());
        logger.info("获取多个线程返回的结果：{}", test2());
    }

    /**
     * 单线程返回结果
     * 线程执行完成以后，获取到线程返回的结果
     *
     * @return 结果
     */
    public static String test1() {
        ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(1);
        // execute没有返回值，submit有返回值
        Future<String> future = threadPool.submit(new Callable<String>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             *
             * @return computed result
             * @throws Exception if unable to compute a result
             */
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                return "SUCCESS";
            }
        });
        logger.info("等待获取结果。。。");
        String result = "";
        try {
            result = future.get();
            logger.info("获取到线程返回结果：{}", result);
            future.isCancelled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("测试获取结果是否会阻塞主线程");
        return result;
    }

    /**
     * 多个线程返回结果
     * 多个线程执行完成以后，获取所有返回结果
     *
     * @return 结果
     */
    public static List<String> test2() {
        ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(5);
        CompletionService<String> completionService = new ExecutorCompletionService<>(threadPool);
        for (int i = 0; i < 5; i++) {
            final int seq = i;
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    TimeUnit.SECONDS.sleep(seq);
                    return "SUCCESS" + seq;
                }
            });
        }
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < 5; i++) {
                String result = completionService.take().get();
                list.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}





























