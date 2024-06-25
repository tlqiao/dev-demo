package com.trace.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Filter;

@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Filter logFilter() {
        SleuthCommonRequestFilter filter = new SleuthCommonRequestFilter();
        filter.setIncludeHeaders(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(5120);
        return filter;
    }
    //setting the sleuthCommonRequestFilter to define include what kind of info in trace
    //eg:if setIncludeHeaders(true),then will show headers info in trace
}
