package com.example.cortex.controller;

import com.example.cortex.dto.request.ProjectInviteRequest;
import com.example.cortex.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptInvite(@RequestParam String token) {
        emailService.acceptInvite(token);
        return ResponseEntity.ok().build();
    }
}
