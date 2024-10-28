package com.example.cortex.dto;
import lombok.*;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private String username;
}
