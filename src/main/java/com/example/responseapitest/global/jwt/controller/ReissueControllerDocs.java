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
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "리프레시 토큰", description = "토큰 API")
public interface ReissueControllerDocs {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n"
                                    + "    \"timestamp\": \"2024-10-22T21:35:03.755865\",\n"
                                    + "    \"isSuccess\": true,\n"
                                    + "    \"message\": \"토큰 재발급 성공\",\n"
                                    + "    \"data\": null\n"
                                    + "}"),
                            schema = @Schema(implementation = SuccessResponse.class)))
    })
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);

}
