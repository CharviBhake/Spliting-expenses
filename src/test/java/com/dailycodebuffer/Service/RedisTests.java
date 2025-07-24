package com.dailycodebuffer.Service;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Disabled
    @Test
    void testSendMail(){
        redisTemplate.opsForValue().set("email","vipul@email.com");
       // Object salary=redisTemplate.opsForValue().get("salary");
        int a=1;
        String salary = redisTemplate.opsForValue().get("salary");
        System.out.println("Salary from Redis: " + salary);
    }
}
