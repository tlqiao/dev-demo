package com.learning.redis.pubsub.service.impl;

import com.learning.redis.pubsub.configuration.ChannelProperties;
import com.learning.redis.pubsub.model.MessageRequest;
import com.learning.redis.pubsub.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

  private final ReactiveRedisTemplate<String, String> stringReactiveRedisTemplate;
  private final ChannelProperties channelProperties;

  @Override
  public Mono<String> publish(MessageRequest request) {
    return stringReactiveRedisTemplate
        .convertAndSend(channelProperties.getHeadline(), request.getText())
        .map(response -> "OK")
        .onErrorReturn("FAILED");
  }
  //通过redisTemplate提供的方法convertAndSend发布消息
}
