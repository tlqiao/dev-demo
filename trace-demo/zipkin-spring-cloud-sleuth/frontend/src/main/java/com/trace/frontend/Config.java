package com.trace.frontend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
}
//You have to register RestTemplate as a bean so that the interceptors get injected.
// If you create a RestTemplate instance with a new keyword, the instrumentation does NOT work.
