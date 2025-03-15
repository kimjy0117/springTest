package com.example.responseapitest.global.jwt.controller;

import com.example.responseapitest.global.apiPayload.code.ApiResponse;
import com.example.responseapitest.global.jwt.service.ReissueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class ReissueController implements ReissueControllerDocs{
    public final ReissueService reissueService;

    @Override
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissueToken(request, response);
    }
}
