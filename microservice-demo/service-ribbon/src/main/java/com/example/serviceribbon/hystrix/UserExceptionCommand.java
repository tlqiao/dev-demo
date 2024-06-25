package com.example.serviceribbon.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserExceptionCommand extends HystrixCommand<String> {

  public static final Logger LOGGER = LoggerFactory.getLogger(UserExceptionCommand.class);

  public UserExceptionCommand() {
    super(HystrixCommandGroupKey.Factory.asKey("userGroup"));
  }

  @Override
  protected String run() throws Exception {
    LOGGER.info("start query exception endpoint");
    URL url = new URL("http://localhost:8064/userForHystrix/exception");
    byte[] result = new byte[1024];
    url.openStream().read(result);
    return new String(result);
  }

  @Override
  protected String getFallback() {
    return "服务降级，暂时不可用";
  }
}
