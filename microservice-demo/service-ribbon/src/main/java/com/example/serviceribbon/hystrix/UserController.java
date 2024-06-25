package com.example.serviceribbon.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userForHystrix")
public class UserController {
  private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value="/timeout",method = RequestMethod.GET)
  public String timeout() throws InterruptedException {
    LOGGER.info("invoking timeout endpoint");
    Thread.sleep(10000L);
    return "success";
  }

  @RequestMapping(value="/exception",method = RequestMethod.GET)
  public String exception() {
    LOGGER.info("invoking exception endpoint");
    if (System.currentTimeMillis() % 2 == 0) {
      throw new RuntimeException("random exception");
    }
    return "success";
  }

  @RequestMapping(value="/command/timeout",method = RequestMethod.GET)
  public String commandTimeout() {
    return new UserTimeoutCommand().execute();
  }

  @RequestMapping(value="/command/exception",method = RequestMethod.GET)
  public String commandException() {
    return new UserExceptionCommand().execute();
  }

  @RequestMapping(value="/hello")
  public String helloWorld(){
    return "hello world";
  }

}
