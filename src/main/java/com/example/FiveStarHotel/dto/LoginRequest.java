package com.example.FiveStarHotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is required for the login")
    private String email;
    @NotBlank(message = "Password is required for the login")
    private String password;
}
