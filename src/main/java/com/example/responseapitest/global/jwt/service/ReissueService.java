package com.example.responseapitest.global.jwt.service;

import com.example.responseapitest.domain.user.repository.UserRepository;
import com.example.responseapitest.global.apiPayload.code.ApiResponse;
import com.example.responseapitest.global.exception.BaseException;
import com.example.responseapitest.global.jwt.util.JwtUtil;
import com.example.responseapitest.global.jwt.exception.status.AuthErrorStatus;
import com.example.responseapitest.global.jwt.exception.status.AuthSuccessStatus;
import com.example.responseapitest.global.redis.RedisUtil;
import com.example.responseapitest.domain.user.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReissueService {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    public ResponseEntity<ApiResponse> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;
        Cookie[] cookies = request.getCookies();

        //쿠키 유무 판별
        if (cookies == null) {
            log.info("쿠키가 존재하지 않음");
            throw new BaseException(AuthErrorStatus._EMPTY_REFRESH_TOKEN.getResponse());
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        //refresh토큰이 없다면 에러 메시지 반환
        if (refresh == null) {
            log.info("refresh 토큰이 존재하지 않음");
            throw new BaseException(AuthErrorStatus._EMPTY_REFRESH_TOKEN.getResponse());
        }

        //refresh토큰이 유효한지 검증
        //만료시간 검증
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            log.info("refresh토큰 시간이 만료되었습니다.");
            throw new BaseException(AuthErrorStatus._EXPIRED_REFRESH_TOKEN.getResponse());
        }

        //토큰이 refresh인지 확인
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            log.info("refresh토큰이 아닙니다.");
            throw new BaseException(AuthErrorStatus._INVALID_REFRESH_TOKEN.getResponse());
        }

        //Redis에 저장되어있는지 확인
        //토큰에서 uesername 추출
        String username = jwtUtil.getUsername(refresh);
        //Redis에서 username을 토대로 refresh토큰을 가져옴
        String redisRefresh = redisUtil.getData(username);

        //redis에 refresh토큰이 저장되어있는지 확인
        if (redisRefresh == null) {
            log.info("DB에 refresh토큰이 존재하지 않습니다.");
            throw new BaseException(AuthErrorStatus._EMPTY_DB_REFRESH_TOKEN.getResponse());
        }

        //redis에 저장된 토큰과 클라이언트 측 refresh토큰이 같은지 확인
        if (!redisRefresh.equals(refresh)) {
            log.info("DB에 저장된 refresh토큰과 클라이언트 측 refresh토큰이 일치하지 않습니다.");
            throw new BaseException(AuthErrorStatus._NOT_EQUAL_REFRESH_TOKEN.getResponse());
        }

        //username을 토대로 user정보 가져오기
        User user = userRepository.findByUsername(username);

        //사용자 정보가 존재하는지 확인
        if (user == null) {
            log.info("DB에 해당 사용자 정보가 존재하지 않습니다.");
            throw new BaseException(AuthErrorStatus._EMPTY_DB_USER.getResponse());
        }

        //JWT토큰 생성
        String newAccessToken = jwtUtil.createAccessToken(user.getUsername(), user.getName(), user.getRole().getRoles(), 1000 * 60 * 30L);//30분
        String newRefreshToken = jwtUtil.createRefreshToken(user.getUsername(),1000 * 60 * 60 * 24 * 7L);//7일

        //redis에 기존 토큰 삭제 후 새 토큰 저장
        redisUtil.deleteData(username);
        addRefreshToken(username, newRefreshToken, 1000 * 60 * 60 * 24 * 7L);

        //응답 설정
        response.addCookie(createCookie("Authorization", newAccessToken));
        response.addCookie(createCookie("refresh", newRefreshToken));

        return ApiResponse.success(AuthSuccessStatus._SUCCESS_REFRESH_TOKEN.getResponse());
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24 * 7);
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
