package com.zy.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zhangy
 * @Date 11:13 2020/4/14
 **/
public class ReentranLockDemo {

    private  static int number = 0;
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 10 ; i++) {
            new Thread(()-> {
                lock.lock();
                 number =  number ++;
                //number =  ++ number ;
                System.out.println(number);
               lock.unlock();
            }).start();
        }



    }


}
