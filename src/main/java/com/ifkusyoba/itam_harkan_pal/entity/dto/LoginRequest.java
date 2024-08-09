package com.ifkusyoba.itam_harkan_pal.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Size(max = 100)
    private String email;
    @NotBlank(message = "Password is required")
    @Size(max = 100)
    private String password;
}
