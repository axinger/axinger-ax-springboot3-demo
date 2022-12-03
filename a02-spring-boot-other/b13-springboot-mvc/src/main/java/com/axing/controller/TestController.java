package com.axing.controller;

import com.axing.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private int count;

    @Autowired
    private TestService testService;

    @GetMapping("/count")
    public Object count1() {
        count++;
        return List.of(count);
    }

    @GetMapping("/count2")
    public Object count2() {
        return List.of(testService.count());
    }
}