package com.example.responseapitest.domain.user.service;

import com.example.responseapitest.domain.user.Role;
import com.example.responseapitest.domain.user.entity.User;
import com.example.responseapitest.domain.user.exception.status.UserErrorStatus;
import com.example.responseapitest.domain.user.exception.status.UserSuccessStatus;
import com.example.responseapitest.domain.user.repository.UserRepository;
import com.example.responseapitest.global.apiPayload.code.ApiResponse;
import com.example.responseapitest.global.exception.BaseException;
import com.example.responseapitest.global.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public ResponseEntity<ApiResponse> updateUser(String role){
        //현재 접속한 사용자 정보 가져옴
        User user = securityUtil.getCurrentUser();

        //user가 비어있을 경우 에러
        if(user == null){
            throw new BaseException(UserErrorStatus._EMPTY_USER_INFORMATION.getResponse());
        }

        //userRole과 바꾸려는 role이 같다면 에러처리
        if(user.getRole().getRoles().equals(role)){
            throw new BaseException(UserErrorStatus._ALREADY_USER_ROLE.getResponse());
        }
        
        //유저 정보 업데이트
        System.out.println("요청 들어온 ROle값: " + Role.valueOf(role));
        user.setRole(Role.valueOf(role.replace("ROLE_", "")));
        userRepository.save(user);

        log.info("사용자 role이 변경됨"+role);
        
        return ApiResponse.success(UserSuccessStatus._SUCCESS_UPDATE_USER_ROLE.getResponse());
    }
}
