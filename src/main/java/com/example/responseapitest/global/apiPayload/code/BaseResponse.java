package com.example.responseapitest.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseResponse {
    HttpStatus getHttpStatus();
    String getMessage();

    default ResponseDTO getResponse() {
        return ResponseDTO.builder()
                .httpStatus(getHttpStatus())
                .message(getMessage())
                .build();
    };
}
