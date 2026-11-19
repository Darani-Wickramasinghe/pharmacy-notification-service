
package com.pharmacy.notification.controller;

import com.pharmacy.notification.entity.Notification;
import com.pharmacy.notification.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pharmacy.notification.dto.CreateNotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Value("${internal.service.secret}")
    private String internalServiceSecret;

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

    @PostMapping
    public Notification createNotification(
            @RequestBody CreateNotificationRequest request,
            @RequestHeader("X-Internal-Secret") String providedSecret) {

        boolean validSecret = MessageDigest.isEqual(
                internalServiceSecret.getBytes(StandardCharsets.UTF_8),
                providedSecret.getBytes(StandardCharsets.UTF_8)
        );

        if (!validSecret) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Invalid internal service credentials"
            );
        }

        return notificationService.createNotification(
                request.getUserId(),
                request.getNotificationType(),
                request.getTitle(),
                request.getMessage(),
                request.getPriority()
        );
    }
}
