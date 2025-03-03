package com.example.responseapitest.domain.swaggerTest.status;

import com.example.responseapitest.global.apiPayload.code.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SwaggerStatus implements BaseResponse {
    _SWAGGER_SUCCESS(HttpStatus.OK, "Swagger success.");

    private final HttpStatus httpStatus;
    private final String message;
}
