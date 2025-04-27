package com.example.demo.model.reservation.DTO;

import com.example.demo.model.reservation.ReservationStatus;

import java.util.UUID;

public class UpdateReservationStatusDTO {
    private UUID reservationId;
    private ReservationStatus status;

    // Getters and setters
    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}