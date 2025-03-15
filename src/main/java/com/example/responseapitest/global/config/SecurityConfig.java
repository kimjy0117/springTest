package com.example.responseapitest.global.config;

import com.example.responseapitest.global.jwt.exception.JwtAccessDeniedHandler;
import com.example.responseapitest.global.jwt.exception.JwtAuthenticationEntryPoint;
//import com.example.responseapitest.global.jwt.exception.ExceptionHandlerFilter;
import com.example.responseapitest.global.jwt.service.PermittedUriService;
import com.example.responseapitest.oauth2.handler.CustomSuccessHandler;
import com.example.responseapitest.oauth2.service.CustomOAuth2UserService;
import com.example.responseapitest.global.jwt.filter.JwtAuthenticationFilter;
import com.example.responseapitest.global.jwt.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final PermittedUriService permittedUriService;
    private final ObjectMapper objectMapper;

    // API 보안 설정 (JWT, OAuth2 등)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //cors 설정
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));

                        return configuration;
                    }
                }));

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/favicon.ico",
                                "/",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api/auth/logout",
                                "/api/token/**",
                                "/login/**").permitAll()

                        .requestMatchers("/api/test/role/admin").hasRole("ADMIN")
                        .requestMatchers("/api/test/role/user").hasRole("USER")
                        .requestMatchers("/api/test/role/guest").hasRole("GUEST")

                        .anyRequest().authenticated())

                //login uri로 리디렉션 막기
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // ✅ EntryPoint를 직접 사용
                        .accessDeniedHandler(jwtAccessDeniedHandler) // ✅ AccessDeniedHandler도 클래스화
                )

        //JWTFilter 추가 (무한 리다이렉션 방지)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, permittedUriService), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new ExceptionHandlerFilter(objectMapper), JwtAuthenticationFilter.class)

        //oauth2
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                )

        //csrf disable
                .csrf((auth) -> auth.disable())
        //Form 로그인 방식 disable
                .formLogin((auth) -> auth.disable())
        //HTTP Basic 인증 방식 disable
                .httpBasic((auth) -> auth.disable())
        //세션 설정 : STATELESS
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
