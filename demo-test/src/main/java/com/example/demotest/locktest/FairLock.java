package com.example.demotest.locktest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuwj
 * @date 2021/7/28 14:00
 * @Description 公平锁：线程排队依次获取执行
 */
public class FairLock implements Runnable {
    /**
     * 设置公平锁，依次每个线程获取一次，true表示设置为公平锁
     */
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            fairLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "，获得锁!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Thread t1 = new Thread(fairLock, "线程1");
        Thread t2 = new Thread(fairLock, "线程2");
        t1.start();
        t2.start();
    }
}
