package com.example.responseapitest.oauth2.handler;

import com.example.responseapitest.global.redis.RedisUtil;
import com.example.responseapitest.oauth2.dto.CustomOAuth2User;
import com.example.responseapitest.global.jwt.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    public CustomSuccessHandler(JwtUtil jwtUtil, RedisUtil redisUtil) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //OAuth2User
        //authentication.getPrincipal()로 로그인한 사용자의 OAuth2 정보를 가져옴
        CustomOAuth2User customUserDetail = (CustomOAuth2User) authentication.getPrincipal();

        System.out.println("customUserDetail = " + customUserDetail.getName());
        System.out.println("customUserDetail = " + customUserDetail.getUsername());

        //authentication.getAuthorities()를 호출하여 사용자의 권한을 가져옴
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createAccessToken(customUserDetail.getUsername(), customUserDetail.getName(), role, 1000 * 60 * 30L); //30분
        String refresh = jwtUtil.createRefreshToken(customUserDetail.getUsername(), 1000 * 60 * 60 * 24 * 7L); //7일

        //Redis에 리프레쉬 토큰 저장
        addRefreshToken(customUserDetail.getUsername(), refresh, 1000 * 60 * 60 * 24 * 7L);

        //응답 설정
        response.addCookie(createCookie("Authorization", access));
        response.addCookie(createCookie("refresh", refresh));
        response.sendRedirect("http://localhost:5173/");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
    
    //Redis에 리프레쉬 토큰 저장
    private void addRefreshToken(String username, String refresh, Long expiredMs){
        redisUtil.setData(username, refresh, expiredMs);
    }
}
