package com.ifkusyoba.itam_harkan_pal.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String idUser;
    private String userEmail;
    private String userNip;
    private String userName;
    private String userPhone;
    private LocalDate userBirthDate;
    private String userPassword;
    private int roleId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
