package com.learning.redis.pubsub.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "message.channel.news")
public class ChannelProperties {
  private String headline;
  private String sport;
}
