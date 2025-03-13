package com.example.responseapitest.domain.user.dto;

import com.example.responseapitest.domain.user.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private String username;
    private String name;
    private String role;
}
