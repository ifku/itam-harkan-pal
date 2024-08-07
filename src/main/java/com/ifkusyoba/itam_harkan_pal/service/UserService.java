package com.ifkusyoba.itam_harkan_pal.service;

import com.ifkusyoba.itam_harkan_pal.model.User;
import com.ifkusyoba.itam_harkan_pal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUser() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
