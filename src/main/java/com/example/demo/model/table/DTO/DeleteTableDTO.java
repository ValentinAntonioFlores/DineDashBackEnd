package com.example.demo.model.table.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DeleteTableDTO {
    private UUID tableId;

    @JsonProperty("restaurantId")
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