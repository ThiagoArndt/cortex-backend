package com.example.cortex.controller;

import com.example.cortex.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/sendNotification")
    @SendTo("/topic/notifications")
    public String sendNotification(String message) {

        notificationService.sendNotification(message);
        return message; 
    }
}
