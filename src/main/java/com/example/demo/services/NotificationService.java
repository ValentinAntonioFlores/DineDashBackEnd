package com.example.demo.services;

import com.example.demo.model.notification.Notification;
import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotificationForClient(UUID clientUserId, String message) {
        Notification notification = new Notification();
        notification.setClientUserId(clientUserId); // Fixed typo here
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public void createNotificationForRestaurant(UUID restaurantUserId, String message) {
        Notification notification = new Notification();
        notification.setRestaurantUserId(restaurantUserId);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotificationsForClient(UUID clientUserId) {
        ClientUser clientUser = new ClientUser();
        clientUser.setIdUsuario(clientUserId);
        return notificationRepository.findByClientUserAndIsReadFalse(clientUser);
    }

    public List<Notification> getUnreadNotificationsForRestaurant(UUID restaurantUserId) {
        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setIdRestaurante(restaurantUserId);
        return notificationRepository.findByRestaurantUserAndIsReadFalse(restaurantUser);
    }

    public void markNotificationAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}