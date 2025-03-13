package com.example.responseapitest.global.jwt;

import com.example.responseapitest.global.exception.BaseException;
import com.example.responseapitest.global.jwt.exception.status.AuthErrorStatus;
import com.example.responseapitest.oauth2.dto.CustomOAuth2User;
import com.example.responseapitest.domain.user.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;

        System.out.println(Arrays.toString(request.getCookies()));

        Cookie[] cookies = request.getCookies();
        // 쿠키가 null인지 확인
        if (cookies == null) {
            log.info("cookies are empty");
            throw new BaseException(AuthErrorStatus._EMPTY_ACCESS_TOKEN.getResponse());
        }

        for (Cookie cookie : cookies) {
            System.out.println("쿠키이름:" + cookie.getName());
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        //Authorization 토큰 검증
        if (authorization == null){
            log.info("토큰이 존재하지 않음");
            throw new BaseException(AuthErrorStatus._EMPTY_ACCESS_TOKEN.getResponse());
        }

        //토큰
        String token = authorization;

        //access 토큰인지 확인
        String category = jwtUtil.getCategory(token);
        
        if(!category.equals("access")){
            log.error("access토큰이 아님");
            throw new BaseException(AuthErrorStatus._INVALID_ACCESS_TOKEN.getResponse());
        }

        // 토큰 검증 시 ExpiredJwtException 발생 가능
        if (jwtUtil.isExpired(token)) {
            log.info("토큰 만료됨");
            throw new BaseException(AuthErrorStatus._EXPIRED_ACCESS_TOKEN.getResponse());
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .role(role)
                .build();

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
