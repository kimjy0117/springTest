package com.example.responseapitest.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private String username;
    private String name;
    private String profileImage;
    private String role;
}
