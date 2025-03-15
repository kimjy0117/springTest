package com.example.responseapitest.domain.user.exception.status;

import com.example.responseapitest.global.apiPayload.code.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessStatus implements BaseResponse {
    _SUCCESS_TEST(HttpStatus.OK, "성공 테스트 확인"),
    
    _SUCCESS_UPDATE_USER_ROLE(HttpStatus.OK, "유저 역할 업데이트 성공"),
    _SUCCESS_GET_USER_INFORM(HttpStatus.OK, "유저 데이터 응답 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
