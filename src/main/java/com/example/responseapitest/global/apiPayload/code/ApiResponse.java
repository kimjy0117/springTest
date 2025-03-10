package com.example.responseapitest.global.apiPayload.code;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
public record ApiResponse(boolean isSuccess, String message, Object data) {

    public static ResponseEntity<ApiResponse> success(ResponseDTO responseDTO, Object data) {
        return ResponseEntity
                .status(responseDTO.httpStatus())
                .body(
                    ApiResponse.builder()
                        .isSuccess(true)
                        .message(responseDTO.message())
                        .data(data)
                        .build()
                );
    }

    public static ResponseEntity<ApiResponse> success(ResponseDTO responseDTO) {
        return ResponseEntity
                .status(responseDTO.httpStatus())
                .body(
                    ApiResponse.builder()
                        .isSuccess(true)
                        .message(responseDTO.message())
                        .data(null)
                        .build()
                );
    }

    public static ResponseEntity<ApiResponse> fail(ResponseDTO responseDTO) {
        return ResponseEntity
                .status(responseDTO.httpStatus())
                .body(
                    ApiResponse.builder()
                        .isSuccess(false)
                        .message(responseDTO.message())
                        .data(null)
                        .build()
                );
    }

    public static ResponseEntity<ApiResponse> fail(String message, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(
                    ApiResponse.builder()
                        .isSuccess(false)
                        .message(message)
                        .data(null)
                        .build()
                );
    }
}