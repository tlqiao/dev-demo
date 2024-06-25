package geektime.spring.reactor.simple.service;

import geektime.spring.reactor.simple.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import geektime.spring.reactor.simple.config.RedisConfig.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class ReactorRedisService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ReactiveStringRedisTemplate redisTemplate;
    private static final String KEY = "USER_INFO";

    ReactiveHashOperations< String,String, String> hashOps = redisTemplate.opsForHash();

    public void reactorRun() {
        List<User> list = jdbcTemplate.query(
                "select * from t_user", (rs, i) ->
                        User.builder()
                                .name(rs.getString("name"))
                                .address(rs.getString("address"))
                                .age(rs.getInt("age"))
                                .build()
        );
        Flux.fromIterable(list)
                .publishOn(Schedulers.single())
                .doOnComplete(() -> log.info("list ok"))
                .flatMap(c -> {
                    log.info("try to put {},{}", c.getName(), c.getAddress());
                    return hashOps.put(KEY, c.getName(), c.getAddress());
                })
                .doOnComplete(() -> log.info("set ok"))
                .concatWith(redisTemplate.expire(KEY, Duration.ofMinutes(1)))
                .doOnComplete(() -> log.info("expire ok"))
                .onErrorResume(e -> {
                    log.error("exception {}", e.getMessage());
                    return Mono.just(false);
                })
                .subscribe(b -> log.info("Boolean: {}", b),
                        e -> log.error("Exception {}", e.getMessage()));

    }
}
