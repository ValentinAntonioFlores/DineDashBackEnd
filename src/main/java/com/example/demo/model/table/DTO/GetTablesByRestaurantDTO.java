package com.example.demo.model.table.DTO;

import java.util.UUID;

public class GetTablesByRestaurantDTO {
    private UUID restaurantId;

    // Getters and setters
    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}