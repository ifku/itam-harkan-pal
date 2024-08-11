package com.ifkusyoba.itam_harkan_pal.controller;

import com.ifkusyoba.itam_harkan_pal.features.auth.dto.LoginRequest;
import com.ifkusyoba.itam_harkan_pal.features.auth.dto.UserResponse;
import com.ifkusyoba.itam_harkan_pal.features.auth.entity.User;
import com.ifkusyoba.itam_harkan_pal.core.WebResponse;
import com.ifkusyoba.itam_harkan_pal.features.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(path = "/auth/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<User>> getAllUser() {
        List<User> users = authService.getAllUser();
        return WebResponse.<List<User>>builder()
                .message("Fetch all user data success")
                .data(users)
                .isSuccess(true)
                .build();
    }

    @PostMapping(path = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        UserResponse userResponse = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return WebResponse.<UserResponse>builder()
                .message("Login successful")
                .data(userResponse)
                .isSuccess(true)
                .build();
    }
}
