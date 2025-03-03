package com.example.responseapitest.domain.swaggerTest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "스웨거", description = "스웨거 API")
public interface SwaggerControllerDocs {

    @Operation(summary = "조회", description = "등록된 게시물을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    public ResponseEntity<com.example.responseapitest.global.apiPayload.code.ApiResponse> getPost(@RequestParam String name,
                                                                                                  @RequestParam String nickname,
                                                                                                  @RequestParam String birthday);
}
