package com.example.serviceribbon.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderHystrixController {

  @Autowired
  private PaymentHystrixService paymentHystrixService;

  @Value("${server.port}")
  private String serverPort;

  @GetMapping("/consumer/payment/hystrix/ok")
  public String paymentInfo_OK(){
    String result = paymentHystrixService.paymentInfo_OK();
    return result;
  }
  //原方法
    /*@GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        log.info("*******result:"+result);
        return result;
    }*/

  @GetMapping("/consumer/payment/hystrix/timeout/{id}")
  @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")  //1.5秒钟以内就是正常的业务逻辑
  })
  public String paymentInfo_TimeOut(@PathVariable("id") int id){
    String result = paymentHystrixService.paymentInfo_TimeOut(id);
    return result;
  }

  //服务降级方法
  public String paymentTimeOutFallbackMethod(@PathVariable("id") int id){
    return "我是消费者80，对付支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,(┬＿┬)";
  }
}
