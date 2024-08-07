package com.ifkusyoba.itam_harkan_pal.controller;

import com.ifkusyoba.itam_harkan_pal.model.dto.WebResponse;
import com.ifkusyoba.itam_harkan_pal.model.User;
import com.ifkusyoba.itam_harkan_pal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/auth/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        return WebResponse.<List<User>>builder()
                .message("Fetch all user data success")
                .data(users)
                .isSuccess(true)
                .build();
    }
}
