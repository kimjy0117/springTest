package com.example.responseapitest.domain.user.controller;

import com.nimbusds.oauth2.sdk.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


@Tag(name = "유저", description = "유저 API")
public interface UserControllerDocs {

    @Operation(summary = "유저 정보 조회", description = "로그인한 유저 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 데이터 응답 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n"
                                    + "    \"timestamp\": \"2024-10-22T21:35:03.755865\",\n"
                                    + "    \"isSuccess\": true,\n"
                                    + "    \"message\": \"유저 데이터 응답 성공\",\n"
                                    + "    \"data\": {\n"
                                    +           "\"email\": \"test@test.com\",\n"
                                    +           "\"name\": \"김서경\",\n"
                                    +           "\"profileImage\": \"https~\"\n"
                                    +       "}\n"
                                    + "}"),
                            schema = @Schema(implementation = SuccessResponse.class)))
    })
    public ResponseEntity<?> getUser();
}
