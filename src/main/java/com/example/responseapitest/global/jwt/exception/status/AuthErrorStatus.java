package com.example.responseapitest.global.jwt.exception.status;

import com.example.responseapitest.global.apiPayload.code.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorStatus implements BaseResponse {
    _CUSTOM_ERROR(HttpStatus.BAD_REQUEST, "에러테스트 요청입니다."),

    _EMPTY_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access 토큰이 존재하지 않습니다."),
    _INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access 토큰 값이 잘못되었습니다."),
    _EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access 토큰이 만료되었습니다."),

    _EMPTY_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh 토큰이 존재하지 않습니다."),
    _EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh 토큰이 만료되엇습니다."),
    _INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh 토큰 값이 잘못되었습니다."),

    _EMPTY_DB_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh 토큰이 DB에 존재하지 않습니다."),
    _NOT_EQUAL_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "DB에 저장된 refresh 토큰과 클라이언트 측 refresh 토큰이 일치하지 않습니다."),

    _EMPTY_DB_USER(HttpStatus.BAD_REQUEST, "DB에 저장된 사용자 정보가 없습니다."),

    _EMPTY_USER_INFORMATION(HttpStatus.UNAUTHORIZED, "사용자 정보를 불러올 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
