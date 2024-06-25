package com.example.multiplesource.controller;

import com.example.multiplesource.model.Response;
import com.example.multiplesource.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping(value = "/getAll")
    public Response getCollegesResponse(){
        Response response = new Response();
        response.setColleges(testService.getAllColleges());
        response.setStudents(testService.getAllStudents());
        return response;
    }
}
