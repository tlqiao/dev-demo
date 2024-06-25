package com.example.sentineldemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private SentinelService sentinelService;

    @GetMapping(value = "/hello/{s}")
    public String apiHello(@PathVariable long s) {
        return sentinelService.hello(s);
    }
}

