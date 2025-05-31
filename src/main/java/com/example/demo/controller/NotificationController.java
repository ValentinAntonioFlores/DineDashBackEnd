package com.example.demo.controller;

import com.example.demo.model.notification.Notification;
import com.example.demo.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Notification>> getClientNotifications(@PathVariable UUID clientId) {
        List<Notification> notifications = notificationService.getUnreadNotificationsForClient(clientId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Notification>> getRestaurantNotifications(@PathVariable UUID restaurantId) {
        List<Notification> notifications = notificationService.getUnreadNotificationsForRestaurant(restaurantId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable UUID notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read.");
    }
}