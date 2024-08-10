package com.ifkusyoba.itam_harkan_pal.service;

import com.ifkusyoba.itam_harkan_pal.dto.UserResponse;
import com.ifkusyoba.itam_harkan_pal.entity.User;
import com.ifkusyoba.itam_harkan_pal.exception.InvalidPasswordException;
import com.ifkusyoba.itam_harkan_pal.exception.UserNotFoundException;
import com.ifkusyoba.itam_harkan_pal.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> getAllUser() {
        try {
            return authRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserResponse login(String email, String password) {
        Optional<User> userOptional = authRepository.findByUserEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getUserPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return UserResponse.builder()
                .idUser(user.getIdUser())
                .userEmail(user.getUserEmail())
                .userNip(user.getUserNip())
                .userName(user.getUserName())
                .userPhone(user.getUserPhone())
                .userBirthDate(user.getUserBirthDate())
                .roleId(user.getRoleId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
