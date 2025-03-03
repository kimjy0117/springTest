package com.example.responseapitest.domain.swaggerTest.controller;

import com.example.responseapitest.domain.swaggerTest.dto.SwaggerRes;
import com.example.responseapitest.domain.swaggerTest.service.SwaggerService;
import com.example.responseapitest.domain.swaggerTest.status.SwaggerStatus;
import com.example.responseapitest.global.apiPayload.code.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SwaggerController implements SwaggerControllerDocs {
    private final SwaggerService swaggerService;

    @GetMapping("/post")
    public ResponseEntity<ApiResponse> getPost(@RequestParam String name,
                                               @RequestParam String nickname,
                                               @RequestParam String birthday) {

        SwaggerRes swaggerRes = swaggerService.getSwagger(name, nickname, birthday);
        return ApiResponse.success(SwaggerStatus._SWAGGER_SUCCESS.getResponse(), swaggerRes);
    }


}
