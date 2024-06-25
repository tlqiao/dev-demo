package com.example.sentineldemo;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class SentinelServiceImp implements SentinelService{
    @Override
    @SentinelResource(value="hello",blockHandler ="exceptionHandler",fallback="helloFallback")
    public String hello(long number){
      return "hello world + " +number;
    }

    public String helloFallback(long number){
        return "opps fallback"+number;
    }

    public String exceptionHandler(long number){
        return "this exception error" +number;
    }

}
