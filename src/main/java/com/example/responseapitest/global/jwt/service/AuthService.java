package com.example.responseapitest.global.jwt.service;

import com.example.responseapitest.global.apiPayload.code.ApiResponse;
import com.example.responseapitest.global.jwt.JWTUtil;
import com.example.responseapitest.global.jwt.exception.status.AuthSuccessStatus;
import com.example.responseapitest.global.redis.RedisUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.web.util.WebUtils.getCookie;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService {
    private final RedisUtil redisUtil;
    private final JWTUtil jwtUtil;

    public ResponseEntity<ApiResponse> logout(HttpServletRequest request, HttpServletResponse response){
        // 1️⃣ 쿠키에서 Refresh Token 가져오기
        String refreshToken = getCookie(request, "refresh");

        // 2️⃣ Refresh Token이 Redis에 저장되어 있다면 삭제
        if (refreshToken != null) {
            String username = jwtUtil.getUsername(refreshToken);
            redisUtil.deleteData(username);
        }

        // 3️⃣ 쿠키에서 AccessToken & RefreshToken 삭제
        deleteCookie(response, "Authorization");
        deleteCookie(response, "refresh");

        return ApiResponse.success(AuthSuccessStatus._SUCCESS_LOGOUT.getResponse());
    }

    private String getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
