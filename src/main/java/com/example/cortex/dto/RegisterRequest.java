package com.example.cortex.dto;

import lombok.*;


@Data
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
