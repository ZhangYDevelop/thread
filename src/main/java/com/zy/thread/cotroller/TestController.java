package com.zy.thread.cotroller;

import com.zy.thread.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/user")
    public User test(@RequestBody User user) {
        Random random = new Random();
        int second = random.nextInt(100);
        Long lon = Long.valueOf(second);
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(lon);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
