package com.trace.frontend;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class FrontendController {
    @Autowired
    private RestTemplate template;
    private String  backendUrl="http://localhost:8081/";
    Logger logger = LoggerFactory.getLogger(FrontendController.class.getName());

    @GetMapping("/callBackend/getMessage")
    public Map<String, String> controller() {
        logger.info("Executing the frontend server home controller");
        logger.info("call backend service getMessageApi");
        Map<String, String> myMap = template.getForObject(backendUrl+"getMessage",Map.class);
        logger.info("return the frontend controller response");
        return myMap;
    }
}
