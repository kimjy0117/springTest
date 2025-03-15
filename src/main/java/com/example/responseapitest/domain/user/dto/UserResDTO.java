package com.example.responseapitest.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResDTO {
    private String name;
    private String email;
    private String profileImage;
}
