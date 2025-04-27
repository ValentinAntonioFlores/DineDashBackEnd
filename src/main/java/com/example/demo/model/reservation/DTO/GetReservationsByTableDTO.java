package com.example.demo.model.reservation.DTO;

import java.util.UUID;

public class GetReservationsByTableDTO {
    private UUID tableId;

    // Getters and setters
    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }
}