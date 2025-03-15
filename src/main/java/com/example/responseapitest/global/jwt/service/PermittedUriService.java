package com.example.responseapitest.global.jwt.service;

import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;

@Service
public class PermittedUriService {
    private final PathPatternParser patternParser = new PathPatternParser();

    public static final String[] PERMITTED_URI = {
            "/favicon.ico",
            "/",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api/auth/logout",
            "/api/token/**",
            "/login/**"};

    public boolean isPermittedURI(String requestURI) {
        return Arrays.stream(PERMITTED_URI)
                .map(patternParser::parse)
                .anyMatch(pathPattern -> pathPattern.matches(PathContainer.parsePath(requestURI)));
    }
}
