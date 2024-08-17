//package com.ifkusyoba.itam_harkan_pal.features.auth.service;
//
//import com.ifkusyoba.itam_harkan_pal.core.exception.InvalidPasswordException;
//import com.ifkusyoba.itam_harkan_pal.core.exception.UserNotFoundException;
//import com.ifkusyoba.itam_harkan_pal.features.auth.dto.UserResponse;
//import com.ifkusyoba.itam_harkan_pal.features.auth.entity.User;
//import com.ifkusyoba.itam_harkan_pal.features.auth.repository.AuthRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class AuthService {
//    private final AuthRepository authRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
//        this.authRepository = authRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public UserResponse login(String email, String password) {
//        Optional<User> userOptional = authRepository.findByUserEmail(email);
//        if (userOptional.isEmpty()) {
//            throw new UserNotFoundException("User not found");
//        }
//
//        User user = userOptional.get();
//        if (!passwordEncoder.matches(password, user.getUserPassword())) {
//            throw new InvalidPasswordException("Invalid password");
//        }
//
//        return UserResponse.builder()
//                .idUser(user.getIdUser())
//                .userEmail(user.getUserEmail())
//                .userNip(user.getUserNip())
//                .userName(user.getUserName())
//                .userPhone(user.getUserPhone())
//                .userBirthDate(user.getUserBirthDate())
//                .roleId(user.getRoleId().getIdRole())
//                .createdAt(user.getCreatedAt())
//                .updatedAt(user.getUpdatedAt())
//                .build();
//    }
//}
