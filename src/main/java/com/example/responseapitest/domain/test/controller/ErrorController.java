package com.example.responseapitest.domain.test.controller;

import com.example.responseapitest.domain.test.exception.status.ErrorTestStatus;
import com.example.responseapitest.domain.test.exception.status.SuccessTestStatus;
import com.example.responseapitest.global.apiPayload.code.ApiResponse;
import com.example.responseapitest.global.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> errorTest(@RequestParam String code){
        if(code.equals("error")){
            throw new BaseException(ErrorTestStatus._CUSTOM_ERROR.getResponse());
        } else if (code.equals("unauth")) {
            throw new SecurityException();

        } else if(code.equals("conflict")){
            throw new IllegalStateException();
        }

        return ApiResponse.success(SuccessTestStatus._SUCCESS_TEST.getResponse());
    }
}
