package com.example.demo.model.reservation.DTO;

import com.example.demo.model.reservation.ReservationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationSimpleDTO {
    private UUID reservationId;
    private String restaurantName;
    private String reservationStatus;
    private UUID userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public ReservationSimpleDTO(UUID reservationId, String restaurantName, String reservationStatus, UUID userId, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservationId = reservationId;
        this.restaurantName = restaurantName;
        this.reservationStatus = reservationStatus;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // getters and setters

    public UUID getReservationId() {
        return reservationId;
    }
    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
