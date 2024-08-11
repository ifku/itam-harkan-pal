package com.ifkusyoba.itam_harkan_pal.core.seeder;

import com.ifkusyoba.itam_harkan_pal.features.auth.entity.Role;
import com.ifkusyoba.itam_harkan_pal.features.auth.entity.User;
import com.ifkusyoba.itam_harkan_pal.features.auth.repository.AuthRepository;
import com.ifkusyoba.itam_harkan_pal.features.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(AuthRepository authRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
        seedUsers();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRoleName("Admin");

            Role userRole = new Role();
            userRole.setRoleName("User");

            roleRepository.saveAll(List.of(adminRole, userRole));
        }
    }

    private void seedUsers() {
        if (authRepository.count() == 0) {
            Optional<Role> adminRoleOptional = roleRepository.findByRoleName("Admin");
            Role adminRole = adminRoleOptional.get();

            User testUser = new User();
            testUser.setUserEmail("test@gmail.com");
            testUser.setUserNip("123");
            testUser.setUserName("Test User");
            testUser.setUserPhone("08123456789");
//            testUser.setUserBirthDate(Date.valueOf(LocalDate.of(2000, 1, 1)));
            testUser.setUserPassword(passwordEncoder.encode("admin12345"));
            testUser.setRoleId(adminRole);

            authRepository.saveAll(List.of(testUser));
        }
    }
}
