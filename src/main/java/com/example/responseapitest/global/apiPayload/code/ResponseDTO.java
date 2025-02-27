package com.example.responseapitest.global.apiPayload.code;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ResponseDTO(
        HttpStatus httpStatus,
        String message) {
}