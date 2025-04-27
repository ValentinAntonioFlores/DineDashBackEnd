package com.example.demo.model.table.DTO;

import java.util.UUID;

public class DeleteTableDTO {
    private UUID tableId;
    private UUID restaurantId;

    // Getters and setters
    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}