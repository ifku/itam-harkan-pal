package com.ifkusyoba.itam_harkan_pal.features.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @Deprecated
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_user")
    private String idUser;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "user_email", unique = true)
    private String userEmail;

    @NotBlank(message = "NIP is required")
    @Column(name = "user_nip", unique = true)
    private String userNip;

    @NotBlank(message = "Name is required")
    @Column(name = "user_name")
    private String userName;

    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Invalid phone number")
    @Column(name = "user_phone")
    private String userPhone;

    @NotBlank(message = "Birth Date is required")
    @Column(name = "user_birth_date")
    private LocalDate userBirthDate;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
