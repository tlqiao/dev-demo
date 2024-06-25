package com.learning.redis.pubsub.subscriber;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
//MessageListenerAdapter中适配了此方法，所以能监听到频道上的消息
public class MessageListener {
  public void onReceive(String message) {
    log.info("receive message with new data: {}", message);
  }
}
