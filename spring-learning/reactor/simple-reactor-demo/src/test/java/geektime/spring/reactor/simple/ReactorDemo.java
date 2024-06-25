package geektime.spring.reactor.simple;

import geektime.spring.reactor.simple.service.ReactorRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SpringBootTest
@Slf4j
public class ReactorDemo {
    @Autowired
    ReactorRedisService reactorRedisService;
    @Test
    // will print 1,2,3,4,5,6 and show first complete log info
    public void testOne() {
        Flux.range(1, 6)
                .publishOn(Schedulers.elastic())
                .doOnComplete(() -> log.info("first complete"))
                .subscribe((i) -> System.out.println(i));
    }

    @Test
    public void testTwo() {
        Flux.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.elastic())
                .map(i -> i * 2)
                .subscribe(System.out::println);
    }

    @Test
    public void testThree() {
        Flux.just("foo", "bar")
                .map(str -> {
                    if (str.equals("bar")) {
                        throw new RuntimeException("Error!");
                    }
                    return str.toUpperCase();
                })
                .subscribe(str -> System.out.println("Received: " + str),
                        error -> System.out.println("Error: " + error.getMessage()));

    }

    @Test
    public void testFour() {
        Flux.just("foo", "bar")
                .publishOn(Schedulers.parallel())
                .map(String::toUpperCase)
                .publishOn(Schedulers.elastic())
                .map(str -> str + "!")
                .subscribe(System.out::println);
    }

    @Test
    public void testFive() {
        Flux.just(1, 2, 3)
                .flatMap(i -> Flux.just(i * 2, i * 3))
                .subscribe(System.out::println);
    }

    @Test
    public void testSix() {
        // 创建一个包含 1000 个元素的 Flux 流
        Flux.range(1, 1000)
//                 使用 onBackpressureBuffer 策略
//                .onBackpressureBuffer(10, o -> System.out.println("Buffer overflow: " + o))
//                 使用 onBackpressureDrop 策略
//                .onBackpressureDrop(o -> System.out.println("Dropping: " + o))
//                使用 onBackpressureLatest 策略
//                .onBackpressureLatest()
//                使用 subscribeOn 进行异步订阅
//                .subscribeOn(Schedulers.elastic())
                // 订阅处理元素
                .subscribe(System.out::println);
    }

    @Test
    public void testSeven() {
        Flux.range(1, 10)
                .subscribeOn(Schedulers.elastic())
                .map(i -> {
                    System.out.println(Thread.currentThread().getName() + " mapping " + i);
                    return i * i;
                })
                .subscribe(System.out::println);
    }

    @Test
    public void testEight() {
        Flux.range(1, 10)
                .subscribeOn(Schedulers.newParallel("custom", 2))
                .map(i -> {
                    System.out.println(Thread.currentThread().getName() + " mapping " + i);
                    return i * i;
                })
                .subscribe(System.out::println);
    }

    @Test
    public void testReactorRun() {
        reactorRedisService.reactorRun();
    }

}
