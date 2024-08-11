package com.ifkusyoba.itam_harkan_pal.features.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id_user")
    private String idUser;

    @JsonProperty("user_email")
    private String userEmail;

    @JsonProperty("user_nip")
    private String userNip;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("user_phone")
    private String userPhone;

    @JsonProperty("user_birthdate")
    private LocalDate userBirthDate;

    @JsonProperty("user_role_id")
    private int roleId;

    @JsonProperty("created_at")
    private LocalDate createdAt;

    @JsonProperty("updated_at")
    private LocalDate updatedAt;

}
