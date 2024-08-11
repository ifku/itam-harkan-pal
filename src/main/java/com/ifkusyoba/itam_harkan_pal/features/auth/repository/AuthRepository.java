package com.ifkusyoba.itam_harkan_pal.features.auth.repository;

import com.ifkusyoba.itam_harkan_pal.features.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, String> {
    Optional<User> findByUserEmail(String userEmail);
}

