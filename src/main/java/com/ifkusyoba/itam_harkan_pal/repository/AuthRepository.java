package com.ifkusyoba.itam_harkan_pal.repository;

import com.ifkusyoba.itam_harkan_pal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, String> {
}
