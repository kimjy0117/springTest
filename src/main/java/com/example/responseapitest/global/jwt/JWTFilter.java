package com.example.responseapitest.global.jwt;

import com.example.responseapitest.oauth2.dto.CustomOAuth2User;
import com.example.responseapitest.oauth2.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //login이나 oauth2로 요청이 들어올 경우 넘김(무한 리다이렉션 방지)
        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/login(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authorization = null;

        System.out.println(Arrays.toString(request.getCookies()));

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

        //Authorization 토큰 검증
        if (authorization == null){
            log.info("토큰이 존재하지 않음");
            filterChain.doFilter(request, response);
            //조건의 해당되면 메소드 종료
            return;
        }

        //토큰
        String token = authorization;

        //access 토큰인지 확인
        String category = jwtUtil.getCategory(token);
        
        if(!category.equals("access")){
            log.error("access토큰이 아님");
            filterChain.doFilter(request, response);
        }

        // 토큰 검증 시 ExpiredJwtException 발생 가능 → try-catch로 잡기
        if (jwtUtil.isExpired(token)) {
            log.info("토큰 만료됨");
            filterChain.doFilter(request, response);
            return;
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
