package com.example.cortex.controller;

import com.example.cortex.dto.ProjectInviteRequest;
import com.example.cortex.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/invite")
    public ResponseEntity<Void> inviteUser(@RequestBody ProjectInviteRequest request) {
        emailService.inviteUser(request);
        return ResponseEntity.ok().build();
    }
}
