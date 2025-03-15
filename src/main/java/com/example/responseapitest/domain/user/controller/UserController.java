package com.example.responseapitest.domain.user.controller;

import com.example.responseapitest.domain.user.dto.RoleDTO;
import com.example.responseapitest.domain.user.service.UserService;
import com.example.responseapitest.global.apiPayload.code.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/role")
    public ResponseEntity<ApiResponse> updateRole(@RequestBody RoleDTO roleDTO) {
        return userService.updateUser(roleDTO.getRole());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getUser() {
        return userService.getUser();
    }
}
