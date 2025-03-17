package com.example.responseapitest.global.jwt.controller;

import com.nimbusds.oauth2.sdk.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "로그인", description = "로그인 API")
public interface AuthControllerDocs {

    @Operation(summary = "로그아웃", description = "로그아웃 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n"
                                    + "    \"timestamp\": \"2024-10-22T21:35:03.755865\",\n"
                                    + "    \"isSuccess\": true,\n"
                                    + "    \"message\": \"로그아웃 성공\",\n"
                                    + "    \"data\": null\n"
                                    + "}"),
                            schema = @Schema(implementation = SuccessResponse.class)))
    })
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response);

}
