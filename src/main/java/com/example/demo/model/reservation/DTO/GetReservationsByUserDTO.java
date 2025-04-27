package com.example.demo.model.reservation.DTO;

import java.util.UUID;

public class GetReservationsByUserDTO {
    private UUID userId;

    // Getters and setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}