package com.zy.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author zhangy
 * @Date 10:37 2020/4/14
 **/
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
            System.out.println("车满了，开始出发...");
        });
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 开始上车...");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
