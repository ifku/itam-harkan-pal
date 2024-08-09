package com.ifkusyoba.itam_harkan_pal.controller;

import com.ifkusyoba.itam_harkan_pal.entity.WebResponse;
import com.ifkusyoba.itam_harkan_pal.entity.User;
import com.ifkusyoba.itam_harkan_pal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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

}
