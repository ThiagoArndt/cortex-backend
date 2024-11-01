package com.example.cortex.dto.request;

import lombok.*;


@Data
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
