package com.example.demo.model.reservation.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateReservationDTO {

    @JsonProperty("idTable")
    private UUID idTable;

    @JsonProperty("restaurantId")
    private UUID restaurantUserId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @JsonProperty("userId")
    private UUID idUsuario;

     

    // Getters and setters
    public UUID getTableId() {
        return idTable;
    }

    public void setTableId(UUID idTable) {
        this.idTable = idTable;
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

    public UUID getUserId(){ return idUsuario;}
    public void setUserId(UUID idUsuario){ this.idUsuario = idUsuario; }
}