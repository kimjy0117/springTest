package com.example.responseapitest.domain.swaggerTest.status;

import com.example.responseapitest.global.apiPayload.code.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SwaggerErrorStatus implements BaseResponse {
    _SWAGGER_ERROR(HttpStatus.BAD_REQUEST, "데이터가 비어있습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
