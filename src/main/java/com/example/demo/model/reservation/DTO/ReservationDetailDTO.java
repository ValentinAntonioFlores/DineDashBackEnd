package com.example.demo.model.reservation.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationDetailDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UUID reservationId;
    private String clientUserName;
    private UUID tableId;
    private String status;
    @JsonProperty("userId")
    private UUID idUsuario;

    // Constructor
    public ReservationDetailDTO(UUID reservationId, String clientUserName, UUID tableId, String status, LocalDateTime startTime, LocalDateTime endTime, UUID idUsuario) {
        this.reservationId = reservationId;
        this.clientUserName = clientUserName;
        this.tableId = tableId;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.idUsuario = idUsuario;

    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }
}

