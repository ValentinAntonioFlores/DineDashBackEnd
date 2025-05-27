package com.example.demo.model.reservation.DTO;

import java.util.UUID;

public class ReservationSimpleDTO {
    private String restaurantName;
    private String reservationStatus;
    private UUID userId;

    public ReservationSimpleDTO(String restaurantName, String reservationStatus, UUID userId) {
        this.restaurantName = restaurantName;
        this.reservationStatus = reservationStatus;
        this.userId = userId;
    }

    // getters and setters
    public String getRestaurantName() {
        return restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }
    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public UUID getClientUserId() {
        return userId;
    }
    public void setClientUserId(UUID userId) {
        this.userId = userId;
    }
}
