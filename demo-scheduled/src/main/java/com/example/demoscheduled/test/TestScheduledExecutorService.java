package com.example.demoscheduled.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author xuj231
 * @description ScheduledExecutorService 定时执行线程
 *      ScheduledFuture..cancel(true) 取消线程执行
 * @date 2019/10/17 10:40
 */
public class TestScheduledExecutorService {

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    private static final Map<String,ScheduledFuture> map = new HashMap<>();
    public static void main(String[] args) {
        long start = Runtime.getRuntime().freeMemory();
        for (Integer i=0;i<1000;i++) {
            addScheduled(i);
        }
        long end = Runtime.getRuntime().freeMemory();
        System.out.println("map 占用内存："+(start-end) / (double) 1024 / 1024);
        System.out.println("size:"+map.size());
        map.get("23").cancel(true);
        map.get("56").cancel(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("keyset:"+map.keySet().toString());
        System.out.println("size:"+map.values().size());
        System.out.println("size:"+map.values().toString());
    }
    
    public static void addScheduled(Integer i) {
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
//                System.out.println("线程执行成功:"+i);
                map.remove(i.toString());
            }
        };
        int rest = (i+1)%2+1;
//        System.out.println("定时时间："+rest);
        ScheduledFuture<?> schedule1 = scheduledExecutorService.schedule(run1, rest, TimeUnit.SECONDS);
        map.put(i.toString(),schedule1);
    }
}
