package com.learning.redis.pubsub.service;

import com.learning.redis.pubsub.model.MessageRequest;
import reactor.core.publisher.Mono;

public interface NewsService {

  Mono<String> publish(MessageRequest messageRequest);
}
