package com.ifkusyoba.itam_harkan_pal.service;

import com.ifkusyoba.itam_harkan_pal.entity.User;
import com.ifkusyoba.itam_harkan_pal.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    @Autowired
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public List<User> getAllUser() {
        try {
            return authRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
