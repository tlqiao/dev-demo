package geektime.spring.reactor.simple.controller;

import geektime.spring.reactor.simple.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/redis/set/{key}/{value}")
    public Mono<Boolean> set(@PathVariable String key, @PathVariable String value) {
        return redisService.set(key, value);
    }

    @GetMapping("/redis/get/{key}")
    public Mono<String> get(@PathVariable String key) {
        return redisService.get(key);
    }
}
