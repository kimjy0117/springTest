package com.example.responseapitest.global.jwt.exception.status;

import com.example.responseapitest.global.apiPayload.code.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthSuccessStatus implements BaseResponse {
    _SUCCESS_REFRESH_TOKEN(HttpStatus.OK, "access토큰, refresh토큰 발급 성공");

    private final HttpStatus httpStatus;
    private final String message;
}