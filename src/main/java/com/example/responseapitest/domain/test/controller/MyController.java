package com.example.responseapitest.domain.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class MyController {

    @GetMapping("/my")
    public String myPage(){
        return "my page";
    }
}
