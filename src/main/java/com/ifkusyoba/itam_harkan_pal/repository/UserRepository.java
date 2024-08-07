package com.ifkusyoba.itam_harkan_pal.repository;

import com.ifkusyoba.itam_harkan_pal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
