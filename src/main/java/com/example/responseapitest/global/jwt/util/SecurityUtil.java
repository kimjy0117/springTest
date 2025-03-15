package com.example.responseapitest.global.jwt.util;

import com.example.responseapitest.domain.user.entity.User;
import com.example.responseapitest.domain.user.repository.UserRepository;
import com.example.responseapitest.global.exception.BaseException;
import com.example.responseapitest.global.jwt.exception.status.AuthErrorStatus;
import com.example.responseapitest.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecurityUtil {
    private final UserRepository userRepository;

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BaseException(AuthErrorStatus._EMPTY_USER_INFORMATION.getResponse());
        }

        if (authentication.getPrincipal() instanceof CustomOAuth2User userDetails) {
            String userName = userDetails.getUsername(); // 사용자 식별 아이디 가져오기

            User user = userRepository.findByUsername(userName);

            //저장된 user가 없다면
            if (user == null) {
                throw new BaseException(AuthErrorStatus._EMPTY_DB_USER.getResponse());
            }

            return user;
        }

        throw new BaseException(AuthErrorStatus._EMPTY_USER_INFORMATION.getResponse());
    }
}
