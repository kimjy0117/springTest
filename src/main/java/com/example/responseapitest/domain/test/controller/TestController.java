package com.example.responseapitest.domain.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/role")
public class TestController {

    @GetMapping("/admin")
    public String adminPage(){
        return "I'm admin";
    }

    @GetMapping("/user")
    public String userPage(){
        return "I'm user";
    }

    @GetMapping("/guest")
    public String guestPage(){
        return "I'm guest";
    }

    @GetMapping("/all")
    public String allPage(){
        return "I'm... I'dont know";
    }
}
