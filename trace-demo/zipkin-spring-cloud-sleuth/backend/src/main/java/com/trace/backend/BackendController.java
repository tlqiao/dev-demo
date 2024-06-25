package com.trace.backend;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BackendController {
    @Autowired
    Tracer tracer;
    Logger logger = LoggerFactory.getLogger(BackendController.class.getName());

    @GetMapping("/getMessage")
    public Map<String, String> controller() {
        logger.info("Executing the backend server home controller");
        Map<String, String> myMap = new HashMap<>();
        logger.info("prepare message");
        myMap.put("message", "this is backend server controller");
        createNewSpan();
        logger.info("return the backend controller response");
        return myMap;
    }
    private void createNewSpan() {
        logger.info("begin create new span");
        Span span = tracer.nextSpan().name("newSpan").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            span.tag("newSpan","newTagValue");
            span.event("new span event");
            Thread.sleep(1000L);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        finally {
            span.end();
        }
    }
}
