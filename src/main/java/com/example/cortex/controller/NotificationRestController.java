package com.example.cortex.controller;

import com.example.cortex.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationRestController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/sendNotification")
    public String sendNotification() {
        notificationService.sendNotification("Nova notificação para todos os usuários!");
        return "Notificação enviada!";
    }
}
