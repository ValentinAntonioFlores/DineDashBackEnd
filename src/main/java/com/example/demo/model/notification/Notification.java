package com.example.demo.model.notification;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String message;

    private boolean isRead = false; // Default value

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "client_user_id") // Explicit foreign key column name
    private ClientUser clientUser;

    @ManyToOne
    @JoinColumn(name = "restaurant_user_id") // Explicit foreign key column name
    private RestaurantUser restaurantUser;

    // Getters and setters

    public UUID getClientUserId() {
        return clientUser != null ? clientUser.getIdUsuario() : null;
    }

    public void setClientUserId(UUID id) { // Fixed typo in method name
        if (clientUser == null) {
            clientUser = new ClientUser();
        }
        clientUser.setIdUsuario(id);
    }

    public UUID getRestaurantUserId() {
        return restaurantUser != null ? restaurantUser.getIdRestaurante() : null;
    }

    public void setRestaurantUserId(UUID id) {
        if (restaurantUser == null) {
            restaurantUser = new RestaurantUser();
        }
        restaurantUser.setIdRestaurante(id);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}