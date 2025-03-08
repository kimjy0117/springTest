package com.example.responseapitest.oauth2.handler;

import com.example.responseapitest.oauth2.dto.CustomOAuth2User;
import com.example.responseapitest.global.jwt.JWTUtil;
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
    private final JWTUtil jwtUtil;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
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

        String token = jwtUtil.createJwt(customUserDetail.getUsername(), role, 60 * 60 * 60L);

        response.addCookie(createCookie("Authorization", token));
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
}
