package com.example.demo.model.reservation.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetReservedTablesDTO {

    private UUID restaurantId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public GetReservedTablesDTO() {} // Necesario para deserialización

    public GetReservedTablesDTO(UUID restaurantId, LocalDateTime startTime, LocalDateTime endTime) {
        this.restaurantId = restaurantId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
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

