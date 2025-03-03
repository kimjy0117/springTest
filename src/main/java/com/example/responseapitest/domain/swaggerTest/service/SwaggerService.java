package com.example.responseapitest.domain.swaggerTest.service;

import com.example.responseapitest.domain.swaggerTest.dto.SwaggerRes;
import com.example.responseapitest.domain.swaggerTest.status.SwaggerErrorStatus;
import com.example.responseapitest.global.exception.BaseException;
import org.springframework.stereotype.Service;

@Service
public class SwaggerService {

    public SwaggerRes getSwagger(String name, String nickname, String birthday) {
        if(name == null || nickname == null || birthday.equals("0000")){
            throw new BaseException(SwaggerErrorStatus._SWAGGER_ERROR.getResponse());
        }

        return SwaggerRes.builder()
                .name(name)
                .nickName(nickname)
                .birthday(birthday)
                .build();
    }
}
