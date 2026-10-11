
package com.pharmacy.notification.controller;

import com.pharmacy.notification.entity.Notification;
import com.pharmacy.notification.service.NotificationService;
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
    public List<Notification> getNotifications() {
        // Temporary: uses the sample Admin user's ID.
        // Replace with the authenticated user's ID after JWT security is configured.
        UUID userId = UUID.fromString(
                "a6f46645-d612-4a1f-9c09-25706c402c3d"
        );

        return notificationService.getNotificationsForUser(userId);
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {
        // Temporary: uses the sample Admin user's ID.
        UUID userId = UUID.fromString(
                "a6f46645-d612-4a1f-9c09-25706c402c3d"
        );

        return notificationService.getUnreadNotificationsForUser(userId);
    }
}
