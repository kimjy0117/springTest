package com.example.responseapitest.global.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "리프레시 토큰", description = "토큰 API")
public interface ReissueControllerDocs {

    @Operation(summary = "토큰 재발급", description = "액세스 토큰을 재발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "401", description = "쿠키가 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않음"),
            @ApiResponse(responseCode = "401", description = "DB에 사용자 정보가 존재하지 않음")
    })
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);
}
