package com.example.demo.model.category.DTO;

import java.util.UUID;

public class CreateCategoryDTO {
    private String name;
    private UUID restaurantId;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}
