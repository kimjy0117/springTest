package com.example.responseapitest.domain.test.exception.status;

import com.example.responseapitest.global.apiPayload.code.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorTestStatus implements BaseResponse {
    _CUSTOM_ERROR(HttpStatus.BAD_REQUEST, "에러테스트 요청입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
