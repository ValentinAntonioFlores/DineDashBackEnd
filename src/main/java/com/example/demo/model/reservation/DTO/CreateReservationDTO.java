package com.example.demo.model.reservation.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateReservationDTO {
    private UUID tableId;
    private UUID restaurantUserId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Getters and setters
    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public UUID getRestaurantUserId() {
        return restaurantUserId;
    }

    public void setRestaurantUserId(UUID restaurantUserId) {
        this.restaurantUserId = restaurantUserId;
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