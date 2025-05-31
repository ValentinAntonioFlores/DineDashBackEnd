package com.example.demo.repository;

import com.example.demo.model.notification.Notification;
import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByClientUserAndIsReadFalse(ClientUser clientUser);
    List<Notification> findByRestaurantUserAndIsReadFalse(RestaurantUser restaurantUser);
}