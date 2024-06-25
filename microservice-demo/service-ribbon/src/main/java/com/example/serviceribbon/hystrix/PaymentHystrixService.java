package com.example.serviceribbon.hystrix;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-hystrix")
public interface PaymentHystrixService {
  @GetMapping("/payment/getPaymentOk")
  public String paymentInfo_OK();

  @GetMapping("/payment/getPaymentTimeout/{timeNumber}")
  public String paymentInfo_TimeOut(@PathVariable("timeNumber") int timeNumber);

}
