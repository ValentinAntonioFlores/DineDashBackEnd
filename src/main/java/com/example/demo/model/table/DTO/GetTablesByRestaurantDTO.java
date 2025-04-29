package com.example.demo.model.table.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GetTablesByRestaurantDTO {

    @JsonProperty("restaurantId")
    private UUID restaurantId;

    // Getters and setters
    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}