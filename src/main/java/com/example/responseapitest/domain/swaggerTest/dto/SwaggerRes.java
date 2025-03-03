package com.example.responseapitest.domain.swaggerTest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SwaggerRes(
        @Schema(description = "사용자명", example = "mark")
        String name,

        @Schema(description = "닉네임", example = "물고기")
        String nickName,

        @Schema(description = "생일", example = "2000-01-17")
        String birthday
) {
}
