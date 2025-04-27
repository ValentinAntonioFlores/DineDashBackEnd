package com.example.demo.model.reservation.DTO;

import java.util.UUID;

public class GetReservationByIdDTO {
    private UUID reservationId;

    // Getters and setters
    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }
}
