package com.example.springjpademo.redisdemo.service;

import com.example.springjpademo.redisdemo.model.Student;
import com.example.springjpademo.redisdemo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
public class RedisService {
    @Autowired
    RedisTemplate<String, User> redisTemplate;
    @Autowired
    RedisTemplate<String, Serializable> serializableRedisTemplate;
    @Autowired
    RedisTemplate<String, String> stringRedisTemplate;

    public void saveAndGetFromRedis(User user) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set("user1", user);
        log.info("--get user form redis---" + opsForValue.get("user1"));
    }

    //Redis常用的数据类型：String、Hash、List、Set、zSet(String和Hash类型最常用)
    public void saveAndGetFromStringRedis(String key, String hashKey, String value) {
        HashOperations opsForHash = stringRedisTemplate.opsForHash();
        opsForHash.put(key, hashKey, value);
        log.info("--get info from redis with hash type--" + opsForHash.get(key, hashKey));
    }

    public void saveAndGetFromSerilizableTemplate() {
        Student student = Student.builder()
                .name("zhangsan")
                .age(120)
                .address("chengdu")
                .build();
        ValueOperations valueOperations = serializableRedisTemplate.opsForValue();
        valueOperations.set("firstStudent", student);
        log.info("--get info from serilizableTempate---" + valueOperations.get("firstStudent"));
    }
}
