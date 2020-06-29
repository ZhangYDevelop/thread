package com.zy.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * 模拟并发压测
 */
public class TestConcurrentRequest {

    public static final int totalNum = 100;

    public static final int currentThread = 20;// 并发数

    public static ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(totalNum);
        Semaphore semaphore = new Semaphore(currentThread);
        for (int i = 0; i < totalNum; i++) {
            executorService.execute(()-> {
                try {
                    semaphore.acquire();
                    User user = new User();
                    user.setStart(System.currentTimeMillis());
                    user.setId(UUID.randomUUID().toString());
                    userMap.put(user.getId(), user);

                    System.out.println(System.currentTimeMillis());

                    String userString = HttpUtils.sendPost("http://192.168.106.77:9999/test/user", JSON.toJSONString(user));
                    User result = JSONObject.toJavaObject(JSON.parseObject(userString), User.class);
                    User callbackUser =  userMap.get(result.getId());
                    callbackUser.setEnd(System.currentTimeMillis());

                    userMap.replace(result.getId(), callbackUser);
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            countDownLatch.await();
            executorService.shutdown();
            System.out.println(JSONObject.toJSONString(userMap));
            int time = 0;
            for (Map.Entry<String, User> entry : userMap.entrySet()) {
                User user = entry.getValue();
                time += entry.getValue().getEnd() - entry.getValue().getStart();
            }
            System.out.println("总耗时："  + time / 1000  + " 秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
