
package com.pharmacy.notification.controller;

import com.pharmacy.notification.entity.Notification;
import com.pharmacy.notification.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getNotifications(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return notificationService.getNotificationsForUser(userId);
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return notificationService.getUnreadNotificationsForUser(userId);
    }
}
