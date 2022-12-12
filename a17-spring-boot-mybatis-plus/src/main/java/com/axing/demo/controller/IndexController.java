package com.axing.demo.controller;

import com.axing.demo.domain.Student;
import com.axing.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/student/list")
    public Object index() {
        List<Student> list = studentService.lambdaQuery().list();
        System.out.println("list = " + list);
        return list;
    }
}
