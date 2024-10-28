package com.example.cortex.dto;

import lombok.*;

@Data
@Builder
public class LoginRequest {

    String email;
    String password;
}
