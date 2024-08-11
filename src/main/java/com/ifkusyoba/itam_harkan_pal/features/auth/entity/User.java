package com.ifkusyoba.itam_harkan_pal.features.auth.entity;


import com.ifkusyoba.itam_harkan_pal.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    //    @NotBlank(message = "Birth Date is required")
    @Column(name = "user_birth_date")
    private Date userBirthDate;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "user_password")
    private String userPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role roleId;

}
