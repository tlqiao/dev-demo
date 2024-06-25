package com.example.sentineldemo;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class OrderController
{
    @RequestMapping("/order/message1")
    public String message1(){
        return "测试高并发";
    }
    @RequestMapping("/order/message2")
    public String message2(){
        return "测试高并发";
    }

    @RequestMapping("/order/slow")
    public String slowMessage(){
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException ex){System.out.println(ex.toString());}
        return "this is slow api";
    }

    @RequestMapping("/order/error")
    public String errorMessage(){
        int age=10/0;
        return age + "error api";
    }
    @RequestMapping("/book/getDetails")
    @SentinelResource(value="/book/getDetails",fallback = "handleFallback" , blockHandler = "handleBlocker")
    public String fallback(){
        return "this is book details";
    }
    public String handleFallback(){
        return "too many request, please try later";
    }
    public String handleBlocker(){
        return "this is blocker handler";
    }
}
