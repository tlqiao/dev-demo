package com.example.springjpademo.redis;

import com.example.springjpademo.RedisDemoApplication;
import com.example.springjpademo.redisdemo.model.User;
import com.example.springjpademo.redisdemo.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {RedisDemoApplication.class})
public class RedisServiceTest {
    @Autowired
    RedisService redisService;

    @Test
    public void testUserRedisTemplate() {
        redisService.saveAndGetFromRedis(User.builder()
                .name("taoli")
                .age(140).build());
    }

    @Test
    public void testStringRedisTemplate() {
        redisService.saveAndGetFromStringRedis("one","name","taoli");
    }

    @Test
    public void testSerilizableTemplate() {
        redisService.saveAndGetFromSerilizableTemplate();
    }
}
