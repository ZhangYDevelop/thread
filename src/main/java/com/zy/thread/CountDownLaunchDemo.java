package com.zy.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Author zhangy
 * @Date 10:29 2020/4/14
 *  CountDownLatch是JAVA提供在java.util.concurrent包下的一个辅助类，
 *  可以把它看成是一个计数器，其内部维护着一个count计数，只不过对这个计数器的操作都是原子操作，
 *  同时只能有一个线程去操作这个计数器，CountDownLatch通过构造函数传入一个初始计数值，
 *  调用者可以通过调用CounDownLatch对象的cutDown()方法，来使计数减1；如果调用对象上的await()方法，
 *  那么调用者就会一直阻塞在这里，直到别人通过cutDown方法，将计数减到0，才可以继续执行。
 **/
public class CountDownLaunchDemo {

    public static void main(String[] args)  {
        final CountDownLatch countDownLatch = new CountDownLatch(3);

        new CountAwait(countDownLatch).start();
        new WorkThread(countDownLatch).start();
        new WorkThread(countDownLatch).start();

    }

    private static class WorkThread extends Thread{
        private CountDownLatch countDownLatch;

        public WorkThread(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("Work ThreadName " + Thread.currentThread().getName() + "start");
            countDownLatch.countDown();
            System.out.println("Work ThreadName " + Thread.currentThread().getName() + "end");
        }
    }
    private static class CountAwait extends  Thread {
        private CountDownLatch countDownLatch = null;

        public CountAwait(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("await start");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("await end");
        }
    }
}
