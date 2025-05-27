package com.example.demo.model.reservation.DTO;

import java.util.UUID;

public class ReservationDetailDTO {
    private UUID reservationId;
    private String clientUserName;
    private UUID tableId;
    private String status;

    // Constructor
    public ReservationDetailDTO(UUID reservationId, String clientUserName, UUID tableId, String status) {
        this.reservationId = reservationId;
        this.clientUserName = clientUserName;
        this.tableId = tableId;
        this.status = status;
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

    public UUID getTableId(){
        return tableId;
    }
    public void setTableId(UUID tableId){
        this.tableId = tableId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}

