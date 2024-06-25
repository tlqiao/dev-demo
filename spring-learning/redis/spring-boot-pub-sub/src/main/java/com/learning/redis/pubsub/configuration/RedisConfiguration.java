package com.learning.redis.pubsub.configuration;

import com.learning.redis.pubsub.subscriber.MessageListener;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@AllArgsConstructor
public class RedisConfiguration {
  private final ChannelProperties channelProperties;
//使用MessageListenerAdapter，可以将一个POJO对象的方法适配成消息侦听器，这样就不需要显式地编写MessageListener了。在适配过程中，MessageListenerAdapter将负责将消息转换为方法的参数，并将方法的返回值转换为消息。
//这里适配的是MessageListener中的onReceive()方式，
  @Bean
  public MessageListenerAdapter listenerAdapter(MessageListener messageListener){
    return new MessageListenerAdapter(messageListener, "onReceive");
  }

  @Bean
  public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapter) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(listenerAdapter, new ChannelTopic(channelProperties.getHeadline()));
    return container;
  }

  @Bean
  ReactiveRedisTemplate<String, String> stringReactiveRedisTemplate(ReactiveRedisConnectionFactory redisConnectionFactory) {
    return new ReactiveRedisTemplate<>(redisConnectionFactory, RedisSerializationContext.string());
  }
}
