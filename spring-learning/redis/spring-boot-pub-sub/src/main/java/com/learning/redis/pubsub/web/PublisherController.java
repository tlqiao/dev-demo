package com.learning.redis.pubsub.web;

import com.learning.redis.pubsub.model.MessageRequest;
import com.learning.redis.pubsub.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/news")
@AllArgsConstructor
public class PublisherController {

  private final NewsService newsService;

  @PostMapping("/publish")
  public Mono<ResponseEntity<String>> publish(@RequestBody MessageRequest messageRequest){
    return newsService
        .publish(messageRequest)
        .map(ResponseEntity::ok)
        .subscribeOn(Schedulers.newSingle("main"));
  }
}
