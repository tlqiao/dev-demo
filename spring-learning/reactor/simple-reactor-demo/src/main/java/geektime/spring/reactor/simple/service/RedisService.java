package geektime.spring.reactor.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedisService {
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    public Mono<Boolean> set(String key, String value) {
        return redisTemplate.opsForValue().set(key, value);
    }
    public Mono<String> get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
