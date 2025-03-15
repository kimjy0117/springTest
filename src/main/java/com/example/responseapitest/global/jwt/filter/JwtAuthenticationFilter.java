package com.example.responseapitest.global.jwt.filter;

import com.example.responseapitest.global.jwt.util.JwtUtil;
import com.example.responseapitest.global.jwt.service.PermittedUriService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
    public class JwtAuthenticationFilter extends OncePerRequestFilter {
        private final JwtUtil jwtUtil;
        private final PermittedUriService permittedUriService;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            //인증이 필요 없는 uri 처리
            if (permittedUriService.isPermittedURI(request.getRequestURI())) {
                System.out.println("권한이 필요 없는 URI: " + request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }

            String authorization = null;
            Cookie[] cookies = request.getCookies();
            // 쿠키가 null인지 확인
            if (cookies == null) {
                log.info("cookies are empty");
                filterChain.doFilter(request, response);
                return;
            }

            for (Cookie cookie : cookies) {
                System.out.println("쿠키이름:" + cookie.getName());
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                }
            }

            try {
                if (authorization != null && !jwtUtil.isExpired(authorization)) {
                    Authentication auth = jwtUtil.getAuthentication(authorization);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (ExpiredJwtException e) {
                throw   new JwtException("토큰이 만료되었습니다.", e);
            } catch (JwtException e) {
                throw new JwtException("유효하지 않은 토큰입니다.", e);
            }

        filterChain.doFilter(request, response);
    }
}
