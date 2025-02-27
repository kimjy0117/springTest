package com.example.responseapitest.domain.test.exception.status;

import com.example.responseapitest.global.apiPayload.code.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessTestStatus implements BaseResponse {
    _SUCCESS_TEST(HttpStatus.OK, "성공 테스트 확인");

    private final HttpStatus httpStatus;
    private final String message;
}
