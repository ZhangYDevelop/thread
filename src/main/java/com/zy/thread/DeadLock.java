package com.zy.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zhangy
 * @Date 10:58 2020/4/14
 * 死锁
 **/
public class DeadLock {

    public static void main(String[] args) {
        String lock1 = "lock1";
        String lock2 = "lock2";
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(()-> {
            deadLock(lock1, lock2);
        });
        executorService.execute(()-> {
            deadLock(lock2, lock1);
        });
    }

    public static void deadLock(String lock1, String lock2) {
        synchronized (lock1) {
            try {
                Thread.sleep(100);

                synchronized (lock2) {
                    System.out.println("获得到两把锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
