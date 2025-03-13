package com.example.responseapitest.domain.user.exception.status;

import com.example.responseapitest.global.apiPayload.code.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorStatus implements BaseResponse {
    _CUSTOM_ERROR(HttpStatus.BAD_REQUEST, "에러테스트 요청입니다."),

    _EMPTY_USER_INFORMATION(HttpStatus.BAD_REQUEST, "DB에 유저정보가 존재하지 않습니다."),

    _ALREADY_USER_ROLE(HttpStatus.BAD_REQUEST, "해당 사용자 권한이 이미 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
