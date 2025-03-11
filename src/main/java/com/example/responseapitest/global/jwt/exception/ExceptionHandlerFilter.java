package com.example.responseapitest.global.jwt.exception;

import com.example.responseapitest.global.apiPayload.code.ResponseDTO;
import com.example.responseapitest.global.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            handleException(response, e);
        }
    }

    private void handleException (HttpServletResponse response, BaseException e) throws IOException {
        response.setStatus(e.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        ResponseDTO responseDTO = ResponseDTO.builder()
                .httpStatus(e.getHttpStatus())
                .message(e.getMessage())
                .build();

        response.getWriter().write(mapper.writeValueAsString(responseDTO));
    }
}